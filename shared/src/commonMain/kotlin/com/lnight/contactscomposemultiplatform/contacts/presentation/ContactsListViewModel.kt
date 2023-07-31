package com.lnight.contactscomposemultiplatform.contacts.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.lnight.contactscomposemultiplatform.contacts.domain.Contact
import com.lnight.contactscomposemultiplatform.contacts.domain.ContactDataSource
import com.lnight.contactscomposemultiplatform.contacts.domain.ContactValidator
import com.lnight.contactscomposemultiplatform.core.filterLettersAndTruncate
import com.lnight.contactscomposemultiplatform.core.truncateToLengthAndRemoveParagraphs
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsListViewModel(
    private val contactDataSource: ContactDataSource
): ViewModel() {

    private val _state = MutableStateFlow(ContactListState())
    val state = combine(
        _state,
        contactDataSource.getContacts(),
        contactDataSource.getRecentContacts(20)
    ) { state, contacts, recentContacts ->
        state.copy(
            contacts = contacts,
            recentlyAddedContacts = recentContacts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ContactListState())

    var newContact: Contact? by mutableStateOf(null)
        private set

    fun onEvent(event: ContactListEvent) {
        when(event) {
            ContactListEvent.DeleteContact -> {
                viewModelScope.launch {
                    _state.value.selectedContact?.id?.let { id ->
                        _state.update { it.copy(
                            isSelectedContactSheetOpen = false
                        ) }
                        contactDataSource.deleteContact(id)
                        delay(300L) // Animation delay
                        _state.update { it.copy(
                            selectedContact = null
                        ) }
                    }
                }
            }
            ContactListEvent.DismissContact -> {
                viewModelScope.launch {
                    _state.update { it.copy(
                        isSelectedContactSheetOpen = false,
                        isAddContactSheetOpen = false,
                        firstNameError = null,
                        lastNameError = null,
                        emailError = null,
                        phoneNumberError = null
                    ) }
                    delay(300L) // Animation delay
                    newContact = null
                    _state.update { it.copy(
                        selectedContact = null
                    ) }
                }
            }
            is ContactListEvent.EditContact -> {
                    _state.update { it.copy(
                        selectedContact = null,
                        isAddContactSheetOpen = true,
                        isSelectedContactSheetOpen = false
                    ) }
                    newContact = event.contact
            }
            ContactListEvent.OnAddNewContactClick ->  {
                _state.update { it.copy(
                    isAddContactSheetOpen = true
                ) }
                newContact = Contact(
                    id = null,
                    firstName = "",
                    lastName = "",
                    email = "",
                    phoneNumber = "",
                    photoBytes = null
                )
            }
            is ContactListEvent.OnEmailChanged -> {
                newContact = newContact?.copy(
                    email = event.value.truncateToLengthAndRemoveParagraphs()
                )
                _state.update { it.copy(
                    emailError = null
                ) }
            }
            is ContactListEvent.OnFirstNameChanged -> {
                newContact = newContact?.copy(
                    firstName = event.value.truncateToLengthAndRemoveParagraphs()
                )
                _state.update { it.copy(
                    firstNameError = null
                ) }
            }
            is ContactListEvent.OnLastNameChanged -> {
                newContact = newContact?.copy(
                    lastName = event.value.truncateToLengthAndRemoveParagraphs()
                )
                _state.update { it.copy(
                    lastNameError = null
                ) }
            }
            is ContactListEvent.OnPhoneNumberChanged -> {
                val newNumber = event.value.filterLettersAndTruncate()

                if(newNumber.isNotBlank()) {
                    newContact = newContact?.copy(
                        phoneNumber = newNumber
                    )
                    _state.update {
                        it.copy(
                            phoneNumberError = null
                        )
                    }
                }
            }
            is ContactListEvent.OnPhotoPicked -> {
                newContact = newContact?.copy(
                    photoBytes = event.bytes
                )
            }
            ContactListEvent.SaveContact -> {
                newContact?.let { contact ->
                    val result = ContactValidator.validateContact(contact)
                    val errors = listOfNotNull(
                        result.firstNameError,
                        result.lastNameError,
                        result.emailError,
                        result.phoneNumberError
                    )

                    if(errors.isEmpty()) {
                        _state.update { it.copy(
                            isAddContactSheetOpen = false,
                            firstNameError = null,
                            lastNameError = null,
                            emailError = null,
                            phoneNumberError = null
                        ) }
                        viewModelScope.launch {
                            contactDataSource.insertContact(contact)
                            delay(300L) // Animation delay
                            newContact = null
                        }
                    } else {
                        _state.update { it.copy(
                            firstNameError = result.firstNameError,
                            lastNameError = result.lastNameError,
                            emailError = result.emailError,
                            phoneNumberError = result.phoneNumberError
                        ) }
                    }
                }
            }
            is ContactListEvent.SelectContact -> {
                _state.update { it.copy(
                    selectedContact = event.contact,
                    isSelectedContactSheetOpen = true
                ) }
            }
            else -> Unit
        }
    }

}