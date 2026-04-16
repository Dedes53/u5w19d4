package federicolepore.u5w19d4.services;


import federicolepore.u5w19d4.entities.Author;
import federicolepore.u5w19d4.exceptions.BadRequestException;
import federicolepore.u5w19d4.exceptions.NotFoundException;
import federicolepore.u5w19d4.payloads.AuthorPayload;
import federicolepore.u5w19d4.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AuthorService {

    private final AuthorRepository aRepo;

    public AuthorService(AuthorRepository aRepo) {
        this.aRepo = aRepo;
    }


    //1
    public Author save(AuthorPayload body) {
        if (this.aRepo.existsByEmail(body.getEmail()))
            throw new BadRequestException("L'indirizzo email " + body.getEmail() + "è già in uso");
        Author a = new Author(body.getNome(), body.getCognome(), body.getEmail(), body.getDataDiNascita());
        this.aRepo.save(a);
        return a;
    }


    // 2
    public Page<Author> findAll(int page, int size, String sortBy) {

        if (size > 100) size = 10;
        if (size < 0) size = 0;
        if (page < 0) page = 0;
        // imposto la pagina per determinare quanti risultati ritornare al frontend e comme ordinarli
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.aRepo.findAll(pageable);
    }


    //3
    public Author findById(UUID id) {
        return this.aRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    //4
    public Author findByIdAndUpdate(UUID id, AuthorPayload body) {

        Author found = this.findById(id);
        // verifico che la mail non sia doppia con un altro autore già in uso
        if (!found.getEmail().equals(body.getEmail())) {
            if (this.aRepo.existsByEmail(body.getEmail()))
                throw new BadRequestException("L'indirizzo email " + body.getEmail() + "è già in uso");
        }
        // modifico l'utente trovato
        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());
        found.setDataDiNascita(body.getDataDiNascita());
        found.setAvatarUrl("https://ui-avatars.com/api?name=" + body.getNome() + "+" + body.getCognome());

        Author aUpdated = this.aRepo.save(found);
        log.info("L'utente con id: " + aUpdated.getId() + "è stato salvato correttamente");
        return aUpdated;
    }


    //5
    public void findByIdAndDelete(UUID id) {
        Author found = this.findById(id);
        this.aRepo.delete(found);
    }


}

