# apiItemAcelerator
Estos ejemplos servirán para trabajar con ítems y realizar búsquedas en la api:

| Recurso		|	    Tipo			|Descripción y ejemplo |
| ------------- 	|:-------------:			| ----------------:|
| /items		   	| POST	| Permite el registro de un item Ejemplo: curl -X POST -H "Content-Type: application/json" -d ‘{  title:"Item de test - No Ofertar",  categoryId:"MLA5529", price:10, currencyId:"ARS", availableQuantity:1, buyingMode:"buy_it_now", listingTypeId:"bronze", condition:"new", description: "Item:,  Ray-Ban WAYFARER Gloss Black. New in Box", videoId: "YOUTUBE_ID_HERE", warranty: "12 months by Ray Ban", pictures: [ {"source":"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"} ] }' http://localhost:8080/items |
| /items/{Item_id}	| GET		| Permite la consulta de un item por id. Ejemplo: curl -X GET http://localhost:8080/items/OGO7smMBnTeB1l3KCcg7 |
| /items	| GET 	|	 Permite el listado de todos los items.	Ejemplo: curl -X GET http://localhost:8080/items | 
| /items/{Item_id} | PUT | 	Permite administrar el contenido de un item. Ejemplo: curl -X PUT -d ‘{  id: “GO7smMBnTeB1l3KCcg7”  title:"Item de test - No Ofertar",  categoryId:"MLA5529", price:10, currencyId:"ARS", availableQuantity:1, buyingMode:"buy_it_now", listingTypeId:"bronze", condition:"new", description: "Item:,  Ray-Ban WAYFARER Gloss Black. New in Box", videoId: "YOUTUBE_ID_HERE", warranty: "12 months by Ray Ban", pictures: [ {"source":"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"} ]}’ http://localhost:8080/items/OGO7smMBnTeB1l3KCcg7 |
| /items/{Item_id} | DELETE	| Permite eliminar un item. Ejemplo: curl -X DELETE http://localhost:8080/items/OGO7smMBnTeB1l3KCcg7 |


RESPUESTAS	
------------------------------------------------------------------------------------------------------------------------------
Errores 
            Status code: 200
            {
                 "status": "ERROR",
                  "message": "No existe un item registrado con ese id"
            }

Éxito 
            Status code: 200
            {
                  "status": "SUCCESS",
                  "data": {
                        "id": "OGO7smMBnTeB1l3KCcg7",
                        .
                        .
                        .
                  }
            }	

VISTAS
------------------------------------------------------------------------------------------------------------------------------
    Listado:  http://localhost:8080/listado-item
    Registro: http://localhost:8080/nuevo-item
    Modificación: http://localhost:8080/modificar-item/{Item_id}
  
  **/ Luego de la eliminación de un item debera refrescar la página para verlo impactado /** 
    
