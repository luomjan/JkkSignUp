package kevat25.example.signup.web;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kevat25.example.signup.model.Trainer;
import kevat25.example.signup.model.TrainerRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    TrainerRepository tRepository;

    public UserDetailServiceImpl(TrainerRepository tRepository) {
        this.tRepository = tRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Trainer curruser = tRepository.findByUserName(userName);
        UserDetails user = new org.springframework.security.core.userdetails.User(userName, curruser.getPassword(),
                AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }
}
