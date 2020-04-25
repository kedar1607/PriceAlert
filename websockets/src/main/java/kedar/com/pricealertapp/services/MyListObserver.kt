package kedar.com.pricealertapp.services

import kedar.com.pricealertapp.models.AlertSetUp

interface MyListObserver {
    fun pushUpdate(list: List<AlertSetUp>)
}