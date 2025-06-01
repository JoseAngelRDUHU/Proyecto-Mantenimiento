package services;

import domain.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import repositories.WelcomeRepository;

@Service
@Transactional
public class WelcomeService {

    @Autowired
    WelcomeRepository welcomeRepository;

    public Reader findOne(int readerID){
        return welcomeRepository.findOne(readerID);
    }

    public Reader save(Reader reader) {
        Assert.isTrue(reader.getId() == 0, "Debe ser nuevo");
        String rawPassword = reader.getUserAccount().getPassword();
        String md5 = DigestUtils.md5DigestAsHex(rawPassword.getBytes());
        reader.getUserAccount().setPassword(md5);
        return welcomeRepository.save(reader);
    }
}
