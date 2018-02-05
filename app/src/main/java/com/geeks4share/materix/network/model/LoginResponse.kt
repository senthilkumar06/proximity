package com.geeks4share.materix.network.model

/**
 * Created by senthil-zt121 on 06/02/18.
 */
data class LoginResponse (
        var token: String = "",
        var name: String = "",
        var email: String = "",
        var picture: String = ""
)