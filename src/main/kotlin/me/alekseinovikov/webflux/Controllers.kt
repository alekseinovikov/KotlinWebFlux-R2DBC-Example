package me.alekseinovikov.webflux

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    suspend fun create(@RequestBody login: String): Long =
        userService.create(login)

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long): ResponseEntity<String> =
        userService.findById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

}