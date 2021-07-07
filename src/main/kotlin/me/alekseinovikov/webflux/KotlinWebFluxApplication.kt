package me.alekseinovikov.webflux

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class KotlinWebFluxApplication {

    @Autowired
    private lateinit var userRepository: UserRepository


    @PostConstruct
    fun init() {
        runBlocking {
            userRepository.initDB()
        }
    }

}

fun main(args: Array<String>) {
    runApplication<KotlinWebFluxApplication>(*args)
}
