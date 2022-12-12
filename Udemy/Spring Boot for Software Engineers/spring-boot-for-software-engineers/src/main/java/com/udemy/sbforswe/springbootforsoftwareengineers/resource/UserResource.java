package com.udemy.sbforswe.springbootforsoftwareengineers.resource;

import com.udemy.sbforswe.springbootforsoftwareengineers.config.ErrorMessage;
import com.udemy.sbforswe.springbootforsoftwareengineers.model.User;
import com.udemy.sbforswe.springbootforsoftwareengineers.service.UserService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/users")
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<User> fetchUsers(@QueryParam("gender") String gender){
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @RequestMapping(method= RequestMethod.GET, path ="/{userUUID}")
    public ResponseEntity<?> fetchUser(@PathVariable("userUUID") UUID userUUID){
        Optional<User> userOptional = userService.getUser(userUUID);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("The User with the ID " + userUUID + " was not found. Please Try Again!"));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user){
        int result = userService.insertUser(user);
        return getIntegerResponseEntity(result);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        return getIntegerResponseEntity(result);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{userUUID}")
    public ResponseEntity<Integer> deleteUser(@PathVariable("userUUID") UUID userUUID){
        int result = userService.removeUser(userUUID);
        return getIntegerResponseEntity(result);
    }

    private static ResponseEntity<Integer> getIntegerResponseEntity(int result) {
        if(result == 1){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}


