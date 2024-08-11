package com.thangtien.firstapp.ultil

class UserService {
    fun getUser(id: Int): User? {
        // Lấy user từ database
        return User(id, "User $id")
    }

    fun updateUser(user: User) {
        // Cập nhậtuser trong database
    }

    fun getId(user: User): Int {
        return user.id
    }

    fun deleteUser(id: Int) {
        // Xóa user khỏi database
    }
}

data class User(val id: Int, val name: String)
