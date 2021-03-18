package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostPage;
import com.springboot.blog.entity.PostSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PostCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    public PostCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Post> findAllWithFilters(PostPage postPage, PostSearchCriteria postSearchCriteria){
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Predicate predicate = getPredicate(postSearchCriteria, postRoot);
        criteriaQuery.where(predicate);
        setOrder(postPage, criteriaQuery, postRoot);

        TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(postPage.getPageNumber() + postPage.getPageSize());
        typedQuery.setMaxResults(postPage.getPageSize());

        Pageable pageable = getPageable(postPage);

        long pageCount = getPageCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, pageCount);
    }

    private Predicate getPredicate(PostSearchCriteria postSearchCriteria, Root<Post> postRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(postSearchCriteria.getTitle())){
            predicates.add(
                    criteriaBuilder.like(postRoot.get("title"), "%" + postSearchCriteria.getTitle() + "%")
            );
        }

        if(Objects.nonNull(postSearchCriteria.getAuthor())){
            predicates.add(
                    criteriaBuilder.like(postRoot.get("author"), "%" + postSearchCriteria.getAuthor() + "%")
            );
        }

        if(Objects.nonNull(postSearchCriteria.getTagName())){
            predicates.add(
                    criteriaBuilder.like(postRoot.get("tagName"), "%" + postSearchCriteria.getTagName() + "%")
            );
        }

        if(Objects.nonNull(postSearchCriteria.getExcerpt())){
            predicates.add(
                    criteriaBuilder.like(postRoot.get("excerpt"), "%" + postSearchCriteria.getExcerpt() + "%")
            );
        }

        if(Objects.nonNull(postSearchCriteria.getContent())){
            predicates.add(
                    criteriaBuilder.like(postRoot.get("content"), "%" + postSearchCriteria.getContent() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PostPage postPage, CriteriaQuery<Post> criteriaQuery, Root<Post> postRoot) {
        if(postPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(postRoot.get(postPage.getSortByTitle())));
        }
        else{
            criteriaQuery.orderBy(criteriaBuilder.desc(postRoot.get(postPage.getSortByTitle())));
        }
    }


    private Pageable getPageable(PostPage postPage) {
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortByTitle());
        return PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
    }

    private long getPageCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(long.class);
        Root<Post> countRoot = countQuery.from(Post.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
