package consys.onlineexam.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    private List<ItemDetail> itemList = new ArrayList();
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemDetail> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<ItemDetail> itemList) {
        this.itemList = itemList;
    }
}
