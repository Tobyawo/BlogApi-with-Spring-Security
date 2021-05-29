package com.fb2.fb;


import com.fb2.fb.model.User;
import com.fb2.fb.repository.UserRepository;
import com.fb2.fb.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class) // using Mockito extension class
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;



    @Test
    void shouldReturnFindAll() {
        List<User> datas = new ArrayList();
        datas.add(new User(1l,"oluwa","tobi","tobi@mail.com","1234"));
        datas.add(new User(2l,"oluwa2","tobi2","tobi2@mail.com","1234"));
        datas.add(new User(3l,"oluwa3","tobi3","tobi3@mail.com","1234"));

        given(userRepository.findAll()).willReturn(datas);

        List<User> expected = userService.AllUser();

        assertEquals(expected, datas);
    }

    @Test
    void findUserByEmail(){
        final String email = "tobi@mail.com";
        final User user = new User(1l,"oluwa","tobi","tobi@mail.com","teten");

        given(userRepository.getUserByEmail(email)).willReturn(user);

        final User expected  =userService.getUserByEmail(email);

        assertThat(expected).isNotNull();

    }


    @Test
    void getUserByEmailAndPassword(){
        final String email = "tobi@mail.com";
        final String password = "1234";
        final User user = new User(1l,"oluwa","tobi","tobi@mail.com","1234");

        given(userRepository.getUserByEmailAndPassword(email, password)).willReturn(user);

        final User expected  =userService.getUserByEmailAndPassword(email, password);

        assertThat(expected).isNotNull();

    }



}
