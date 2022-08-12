package com.fov.domain.interactors.authentication


import com.fov.domain.database.daos.UserDao
import com.fov.domain.database.models.RecentUserSearch
import com.fov.domain.database.models.User
import com.fov.domain.models.authentication.users.DeleteAccountDTO
import com.fov.domain.models.authentication.users.DisableAccountDTO
import com.fov.domain.models.authentication.users.EditUserDTO
import com.fov.domain.remote.apollo.users.ApolloUsersService
import com.fov.domain.remote.apollo.users.ApolloUsersServiceImpl
import com.fov.domain.repositories.authentication.AuthenticationRepository
import com.fov.domain.utils.constants.QueryConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class Authenticate  constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val  graphQLService : ApolloUsersService,
    private val userDao: UserDao
){
      suspend fun getCurrentUser(getUser : (user : User) -> Unit) {
          withContext(Dispatchers.IO) {
              userDao.getUser().collect {
                  getUser(it)
              }
          }
      }
      suspend fun signUp(
         userName: String,
         fullName: String,
         email: String,
         password: String,
         phoneNumber: String,
         countryId: Int
     ) = authenticationRepository.signUp(
         userName,
         fullName,
         email,
         password,
         phoneNumber,
         countryId
     )

      suspend fun signIn(
         emailAddress: String,
         password: String,
         restore: Boolean
     ) = authenticationRepository.signIn(
         emailAddress,
         password,
         restore
     )

     suspend fun socialSignIn(
         fullName: String,
         username: String,
         emailAddress: String,
         service: String,
         token : String,
         profileImageUrl : String?,
         isFirstTime: Boolean
     ) = authenticationRepository.socialSignIn(
          fullName = fullName,
          username = username,
          emailAddress = emailAddress,
          service = service,
          token = token,
          profileImageUrl = profileImageUrl,
          isFirstTime = isFirstTime

     )


      suspend fun logout() = authenticationRepository.logout()

      suspend fun forgotPassword(email: String) = authenticationRepository.forgotPassword(email)

     suspend fun resendCode(userId: String, isReset : Boolean) = authenticationRepository.resendCode(userId,isReset)

      suspend fun resetPassword(
         email: String,
         code: String,
         password: String,
         restore:Boolean
     ) = authenticationRepository.resetPassword(
         email,
         code,
         password,
          restore
     )

      suspend fun changePassword(
         username: String,
         newPassword: String,
         oldPassword: String
     ) = authenticationRepository.changePassword(
         username,
         newPassword,
         oldPassword
     )

      suspend fun verifyUserCode(userId: String, code: String)
             = withContext(Dispatchers.IO) {
          authenticationRepository.verifyUserCode(userId,code)
      }

      suspend fun refreshToken(token: String, refreshToken: String)
             = withContext(Dispatchers.IO) {
          authenticationRepository.refreshToken(token,refreshToken)
      }



     suspend fun getUserNotifications(userId:String,page: Int, unread: Boolean) = withContext(Dispatchers.IO) {
         authenticationRepository.getUserNotifications(userId,page, unread)
     }

    suspend fun getUserGraph(id: String)= withContext(Dispatchers.IO) {
        graphQLService.getUser(id)
    }

     suspend  fun getUsersGraph(page : Int) = withContext(Dispatchers.IO) {
         graphQLService.getPaginatedUsers(page,QueryConstants.NUM_ROWS)
     }



    suspend fun getNumberUnreadNotifications(id: String) = withContext(Dispatchers.IO){
        authenticationRepository.getNumberUnreadNotifications(id)
    }

    suspend  fun editUser(user: EditUserDTO) {

    }
    suspend  fun deleteAccount(delete: DeleteAccountDTO)  = authenticationRepository.deleteAccount(delete)
    suspend fun disableAccount(disable: DisableAccountDTO) = authenticationRepository.disableAccount(disable)


}