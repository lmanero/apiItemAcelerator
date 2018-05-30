package service;

import dao.ItemDao;
import domain.Item;
import exception.ElasticException;
import exception.ItemException;
import java.util.Collection;

public class ItemServiceImpl implements ItemService{

    final ItemDao itemDao;

    public ItemServiceImpl() {
        this.itemDao = new ItemDao();
    }

    @Override
    public Collection<Item> getItems() throws ItemException {
        try {
            return itemDao.getItems();
        } catch (ElasticException e) {
            throw new ItemException("Error al intentar recuperar todos los items");
        }
    }


    @Override
    public Item getItem(String id) throws ItemException {
        try {
            return itemDao.getItem(id);
        } catch (ElasticException e) {
            throw new ItemException("Error al intentar recuperar el item");
        }
    }

    @Override
    public Item updateItem(Item forEdit) throws ItemException {
        try {
            if (this.getItem(forEdit.getId()) == null)
                throw new ItemException("El item no existe en la base de datos");
            return itemDao.updateItem(forEdit);
        } catch (Exception ex) {
            throw new ItemException("Error al intentar recuperar el item");
        }
    }

    @Override
    public void deleteItem(String id) throws ItemException {
        try {
            itemDao.deleteItem(id);
        } catch (ElasticException e) {
            throw new ItemException("Error al intentar eliminar el item");
        }
    }

    @Override
    public void addItem(Item item) throws ItemException {
        try {
            if (item == null || item.getTitle() == null || item.getDescription() == null)
                throw new ItemException("El item no tiene los datos mínimos para ser creado");
            itemDao.addItem(item);
        } catch (ElasticException e) {
            throw new ItemException("Error al intentar guardar el item");
        }
    }

}
