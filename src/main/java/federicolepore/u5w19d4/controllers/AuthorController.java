package federicolepore.u5w19d4.controllers;


import federicolepore.u5w19d4.entities.Author;
import federicolepore.u5w19d4.payloads.AuthorPayload;
import federicolepore.u5w19d4.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    // 1. POST http://localhost:3001/authors (+ req.body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author save(@RequestBody AuthorPayload body) {
        return this.authorService.save(body);
    }


    // 2. GET http://localhost:3001/authors
    @GetMapping
    public Page<Author> getAuthors(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "nome") String sortBy) {

        return this.authorService.findAll(page, size, sortBy);
    }


    // 3. GET http://localhost:3001/authors/{authorId}
    @GetMapping("/{authorId}")
    public Author getById(@PathVariable UUID id) {
        return this.authorService.findById(id);
    }


    // 4. PUT http://localhost:3001/authors/{authorId} (+ req.body)
    @PutMapping("/{authorId}")
    public Author getByIdAndUpdate(@PathVariable UUID id, @RequestBody AuthorPayload body) {
        return this.authorService.findByIdAndUpdate(id, body);
    }


    // 5. DELETE http://localhost:3001/authors/{authorId}
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id) {
        this.authorService.findByIdAndDelete(id);
    }


}
