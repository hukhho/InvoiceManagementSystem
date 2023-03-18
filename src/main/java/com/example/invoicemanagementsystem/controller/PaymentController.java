package com.example.invoicemanagementsystem.controller;

import com.example.invoicemanagementsystem.entity.ExcelTemplate;
import com.example.invoicemanagementsystem.entity.Payment;
import com.example.invoicemanagementsystem.entity.Users;
import com.example.invoicemanagementsystem.repository.PaymentRepository;
import com.example.invoicemanagementsystem.repository.UsersRepository;
import com.example.invoicemanagementsystem.ultils.Momo.config.Environment;
import com.example.invoicemanagementsystem.ultils.Momo.config.PartnerInfo;
import com.example.invoicemanagementsystem.ultils.Momo.enums.RequestType;
import com.example.invoicemanagementsystem.ultils.Momo.models.PaymentResponse;
import com.example.invoicemanagementsystem.ultils.Momo.models.QueryStatusTransactionResponse;
import com.example.invoicemanagementsystem.ultils.Momo.processor.CreateOrderMoMo;
import com.example.invoicemanagementsystem.ultils.Momo.processor.QueryTransactionStatus;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

@Controller
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PaymentRepository paymentRepository;

    @RequestMapping(value = "/deposit")
    public String createPayment(Model model,
                                HttpSession session,
                                @RequestParam Long amount
                                ) throws Exception {
        try {
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                return "redirect:/auth/login";
            }
            String username = user.getUsername();

            String requestId = String.valueOf(System.currentTimeMillis());

            String orderId = String.valueOf(System.currentTimeMillis()) + "_InvoiceID";


            String orderInfo = username;

            String returnURL = "http://localhost:8080/payment/queryPayment";
            String notifyURL = "http://localhost:8080/payment/queryPayment";

            //GET this from HOSTEL OWNER
            //Exampe owner.getSecretKey
            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);

            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);
            //Payment Method- Phương thức thanh toán
            PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);
            //PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);
            //PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);

            String redirectLink = captureWalletMoMoResponse.getPayUrl().toString();
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setDone(false);
            payment.setSuccess(false);
            payment.setAmount(amount);
            payment.setOrderId(orderId);
            paymentRepository.save(payment);
            return "redirect:" + redirectLink;

        } catch (Exception ex) {
            model.addAttribute("messages", "Error");
        }
        model.addAttribute("messages", "Error");
        return "test2";
    }


    @RequestMapping(value = "/queryPayment")
    public String queryPayment(Model model,
                               HttpSession session,
                               @RequestParam String orderId,
                               @RequestParam String requestId
    ) throws Exception {
        try {
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                return "redirect:/auth/login";
            }

            Users userSave = usersRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid excelTemplate"));;
            model.addAttribute("username", userSave.getUsername());

            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);
            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);

            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);

            String check = queryStatusTransactionResponse.getMessage();
            if (check.equalsIgnoreCase("Successful.")) {
                System.out.println("success: " + queryStatusTransactionResponse.getMessage());
                Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow();
                if (!payment.isSuccess() && !payment.isDone()) {
                    userSave.setAmount(userSave.getAmount() + queryStatusTransactionResponse.getAmount());
                    usersRepository.save(userSave);
                    payment.setSuccess(true);
                    payment.setDone(true);
                    paymentRepository.save(payment);
                    model.addAttribute("message", "Nạp tiền thành công ! Tiền đã được cộng vào tài khoản");

                } else {
                    model.addAttribute("message", "Giao dịch đã hoàn thành!!!");
                }
//              if ()
            }

        } catch (Exception ex) {
            model.addAttribute("message", "Có lỗi đã xảy ra!");
        }
        return "test2";
    }

}
