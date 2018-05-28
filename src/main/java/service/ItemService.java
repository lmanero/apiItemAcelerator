package service;

import domain.Item;

import java.util.Collection;

public interface ItemService {

    public void addItem(Item item);
    public Collection<Item> getItems();
    public Item getItem(String id);
    public Item updateItem(Item item);
    public void deleteItem(String id);

}
