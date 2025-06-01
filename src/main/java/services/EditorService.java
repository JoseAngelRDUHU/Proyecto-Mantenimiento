package services;

import domain.Editor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import repositories.EditorRepository;
import security.Authority;
import security.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class EditorService {

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private ActorService actorService;

    public void delete(Editor editor){
        Assert.isTrue(actorService.isAdmin());
        editorRepository.delete(editor);
    }

    public Collection<Editor> findAll(){
        return editorRepository.findAll();
    }

    public int countByAuthorId(int id){
        return editorRepository.countByAuthorId(id);
    }

    public Editor findOne(int editorID){
        return editorRepository.findOne(editorID);
    }

    public Editor create(){
        Editor editor = new Editor();

        UserAccount userAccount = new UserAccount();

        Authority authority = new Authority();
        authority.setAuthority(Authority.EDITOR);


        userAccount.setAuthorities(new ArrayList<Authority>());
        userAccount.getAuthorities().add(authority);

        editor.setUserAccount(userAccount);

        return editor;
    }

    public Editor save(Editor editor) {
        Assert.isTrue(actorService.isAdmin());
        if (editor.getId() == 0) {
            String rawPassword = editor.getUserAccount().getPassword();
            String md5 = DigestUtils.md5DigestAsHex(rawPassword.getBytes());
            editor.getUserAccount().setPassword(md5);
        } else {
            Editor original = editorRepository.findOne(editor.getId());
            if (editor.getUserAccount().getPassword() == null || editor.getUserAccount().getPassword().isEmpty()) {
                editor.getUserAccount().setPassword(original.getUserAccount().getPassword());
            } else {
                String md5 = DigestUtils.md5DigestAsHex(editor.getUserAccount().getPassword().getBytes());
                editor.getUserAccount().setPassword(md5);
            }
        }

        return editorRepository.save(editor);
    }
}
