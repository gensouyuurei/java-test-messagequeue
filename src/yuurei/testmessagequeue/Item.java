package yuurei.testmessagequeue;

public class Item {
    private int itemID;
    private int groupID;


    public Item (int itemID, int groupID){
        this.itemID = itemID;
        this.groupID = groupID;
    }

    public int getItemID(){
        return this.itemID;
    }

    public int getGroupID() {
        return this.groupID;
    }
}
