package com.example.invoicemanagementsystem.ultils;

import org.apache.poi.ss.util.CellReference;

public class ExcelUtils {
    public static int[] cellReferenceToRowAndColumn(String cellReference) {
        CellReference ref = new CellReference(cellReference);
        int row = ref.getRow();
        int col = ref.getCol();
        return new int[] {row, col};
    }
}