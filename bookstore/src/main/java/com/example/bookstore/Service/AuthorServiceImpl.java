package com.example.bookstore.Service;

import com.example.bookstore.Model.Author;
import com.example.bookstore.Repository.AuthorRepository;
import com.example.bookstore.ServiceInterface.AuthorService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    @Transactional
    public Author saveAuthor (Author author){
        return authorRepository.save(author);
    }
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author updateAuthor(Long id, Author authorDetails) {
        Optional<Author> exisitingAuthorOptional = getAuthorById(id);
        if(exisitingAuthorOptional.isPresent()){
            Author exisitingAuthor = exisitingAuthorOptional.get();
            exisitingAuthor.setFirstName(authorDetails.getFirstName());
            exisitingAuthor.setLastName(authorDetails.getLastName());
            exisitingAuthor.setBirthDate(authorDetails.getBirthDate());
            exisitingAuthor.setBooks(authorDetails.getBooks());
            return authorRepository.save(exisitingAuthor);
        }else {
            return null;
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findByLastNameContainingIgnoreCase(String lastName) {
        return authorRepository.findByLastNameContainingIgnoreCase(lastName);

    }

    @Override
    public List<Author> findByFirstNameContainingIgnoreCase(String firstName) {
        return authorRepository.findByFirstNameContainingIgnoreCase(firstName);

    }

}
