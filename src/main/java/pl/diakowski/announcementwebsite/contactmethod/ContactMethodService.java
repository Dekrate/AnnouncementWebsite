package pl.diakowski.announcementwebsite.contactmethod;

import org.springframework.stereotype.Service;

@Service
public class ContactMethodService {
    private final ContactMethodRepository contactMethodRepository;

    public ContactMethodService(ContactMethodRepository contactMethodRepository) {
        this.contactMethodRepository = contactMethodRepository;
    }

    // to do
    public void addContactMethodService() {

    }
}
