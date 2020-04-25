package kedar.com.pricealertapp.services

import androidx.lifecycle.LiveData


interface ConnectionEstablisher {
    fun connectionStarted(data: LiveData<String>)
}