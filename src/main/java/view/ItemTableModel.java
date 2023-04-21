package view;

import model.Item;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTableModel extends AbstractTableModel {
    private List<Item> items;
    private final String[] columnNames = {"Název", "Cena", "Množství", "Kategorie"};

    public ItemTableModel() {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = items.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.getName();
            case 1:
                return item.getPrice();
            case 2:
                return item.getQuantity();
            case 3:
                return item.getCategory();
            default:
                return null;
        }
    }

    public void setItems(List<Item> items) {
        this.items = items;
        fireTableDataChanged();
    }
}
