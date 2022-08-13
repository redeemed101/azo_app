package com.fov.authentication.events

sealed class SettingsEvent {
    object GoToManageAccount : SettingsEvent()
    object GoToMyWallet : SettingsEvent()
    object GoToInviteFriends : SettingsEvent()
    object GoToRateApp : SettingsEvent()
    object GoToHelp : SettingsEvent()
    object GoToPrivacy : SettingsEvent()
    object GoToAbout : SettingsEvent()
    object GoToPersonalInformation : SettingsEvent()
    object GoToSubscription : SettingsEvent()
    object GoToSwitchToArtist : SettingsEvent()
    object GoToDeleteAccount : SettingsEvent()
    object GoToDisableAccount : SettingsEvent()

    data class DisableReasonChanged(val reason : String) : SettingsEvent()
    data class DeleteReasonChanged(val reason : String) : SettingsEvent()
    data class PasswordChanged(val password : String) : SettingsEvent()

    object DisableAccount : SettingsEvent()
    object DeleteAccount : SettingsEvent()
}