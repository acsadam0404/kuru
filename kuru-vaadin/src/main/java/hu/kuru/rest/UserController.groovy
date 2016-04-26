package hu.kuru.rest

import hu.kuru.user.User
import hu.kuru.user.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/users")
@RestController
class UserController {
    @Autowired
    UserRepo userRepo

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getUser(
            @PathVariable(value = "username") String username) {
        return new ResponseEntity<User>(userRepo.findByUsername(username), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getUsers() {
        return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity login(@RequestBody User user) {
        User found = userRepo.findByUsername(user.username)
        if (found && found.password == user.password) {
            return new ResponseEntity(HttpStatus.OK)
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN)
    }


}
