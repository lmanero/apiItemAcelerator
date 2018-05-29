package service;

import domain.Item;
import exception.ItemException;

import java.util.Collection;

public interface ItemService {

    public void addItem(Item item) throws ItemException;
    public Collection<Item> getItems() throws ItemException;
    public Item getItem(String id) throws ItemException;
    public Item updateItem(Item item) throws ItemException;
    public void deleteItem(String id) throws ItemException;

}
