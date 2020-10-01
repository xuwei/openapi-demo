package co.zip.candidate.userapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserApiApplication

fun main(args: Array<String>) {
	runApplication<UserApiApplication>(*args)
}
