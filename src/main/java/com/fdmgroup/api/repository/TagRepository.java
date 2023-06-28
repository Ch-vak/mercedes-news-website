package com.fdmgroup.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.api.model.Tag;

/**
 * The repository interface for managing Tag entities. Provides methods for
 * filtering and managing Tag data.
 *
 * <p>
 * This interface extends the JpaRepository interface, providing basic CRUD
 * operations for the Tag entity.
 * </p>
 *
 * <p>
 * The TagRepository interface also includes additional custom methods for
 * advanced filtering.
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.fdmgroup.api.model.Tag
 * @author Condelizza Kablan
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Retrieves a Tag by its unique identifier.
     *
     * @param id The unique identifier of the Tag.
     * @return An Optional containing the Tag if found, or an empty Optional if not found.
     */
    Optional<Tag> findById(long id);

    /**
     * Retrieves a Tag by its name, performing a case-insensitive search.
     *
     * @param name The name of the Tag to search for.
     * @return An Optional containing the Tag if found, or an empty Optional if not found.
     */
    @Query("select t from Tag t where lower(t.name) like concat('%', lower(:name), '%')")
    Optional<Tag> findTagByName(String name);

    /**
     * Checks if a Tag exists based on its unique identifier.
     *
     * @param id The unique identifier of the Tag.
     * @return true if the Tag exists, false otherwise.
     */
    boolean existsById(long id);

    /**
     * Deletes a Tag by its unique identifier.
     *
     * @param id The unique identifier of the Tag to delete.
     */
    void deleteById(long id);
}
