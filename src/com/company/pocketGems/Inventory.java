package com.company.pocketGems;

import java.util.LinkedList;
import java.util.List;

class Item {

    public boolean IsQuestItem() {
        return true;
    }
}
public class Inventory {
    //if they are not intend to accessible out side the class, should define as private
    public List<Item> items; // in our current game design, each item is either a quest item or a normal item
    public List<Item> questItems; // for convenience, a subset of items, containing only quest items
    public List<Item> normalItems; // for convenience, a subset of items, containing only normal items

    // the user interface will display the last item we picked up
    private Item lastItemCollected;

    //init; should implement LinkedList because LinkedList has better performance in adding and removing elements
    public Inventory() {
        this.items = new LinkedList<>();
        this.questItems = new LinkedList<>();
        this.normalItems = new LinkedList<>();
        this.lastItemCollected = null;
    }

    public Item LastItemCollected() { //getter naming
        return lastItemCollected;
    }

    public void getitem(Item i, int quantity) { //naming standard // null check
        for(int x = 0; x < quantity; ++x) {
            items.add(i);
            if (i.IsQuestItem()) {
                questItems.add(i);
            } else {
                normalItems.add(i);
            }
        }

//        AchievementSystem.instance.DidModifyItem("gain", i.identifier, quantity);
        didpickupitem(i);
    }

    public void loseitem(Item i, int quantity) { //need to handle corner case: i == null
        //could break the loop when list.remove() == false, i.e. no more i in the list
        for(int x = 0; x < quantity; ++x) {
            items.remove(i);
            if (i.IsQuestItem()) {
                questItems.remove(i);
            } else {
                normalItems.remove(i);
            }
        }

//        AchievementSystem.instance.DidModifyItem("lose", i.identifier, quantity);
    }

    public void didpickupitem(Item i) {
        lastItemCollected = i;
    }
}