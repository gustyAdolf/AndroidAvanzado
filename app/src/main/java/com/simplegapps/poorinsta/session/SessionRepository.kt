package com.simplegapps.poorinsta.session

interface SessionRepository {

    fun getSession(): Session?

    fun saveSession(session: Session)
}