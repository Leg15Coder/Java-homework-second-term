package com.example.javaHomeworkSecondTerm.repository;

import com.example.javaHomeworkSecondTerm.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class InMemoryUsersRepository implements UsersRepository {
  private final RestTemplate restTemplate = new RestTemplate();
  private final WebClient webClient = WebClient.create();
  private final Map<Long, User> users = new ConcurrentHashMap<>();
  private final AtomicLong currentId = new AtomicLong(1);

  private static final String[] urls = {"https://yandex.ru/", "https://www.youtube.com/", "https://ru.wikipedia.org/"};

  @Override
  public void flush() {
    return;
  }

  @Override
  public <S extends User> S saveAndFlush(S entity) {
    entity.setId(currentId.getAndIncrement());
    users.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
    entities.forEach(this::saveAndFlush);
    return List.copyOf((List<S>) entities);
  }

  @Override
  public void deleteAllInBatch(Iterable<User> entities) {
    entities.forEach(user -> users.remove(user.getId()));
  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {
    longs.forEach(users::remove);
  }

  @Override
  public void deleteAllInBatch() {
    users.clear();
  }

  @Override
  public User getOne(Long aLong) {
    return users.get(aLong);
  }

  @Override
  public User getById(Long aLong) {
    return users.get(aLong);
  }

  @Override
  public User getReferenceById(Long aLong) {
    return getById(aLong);
  }

  @Override
  public <S extends User> Optional<S> findOne(Example<S> example) {
    return users.values().stream()
        .filter(user -> example.getProbe().equals(user))
        .findFirst()
        .map(user -> (S) user);
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example) {
    return users.values().stream()
        .filter(user -> example.getProbe().equals(user))
        .map(user -> (S) user)
        .toList();
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
    List<S> result = findAll(example);
    return result;
  }

  @Override
  public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
    List<S> result = findAll(example);
    int start = Math.min((int) pageable.getOffset(), result.size());
    int end = Math.min((start + pageable.getPageSize()), result.size());
    return new PageImpl<>(result.subList(start, end), pageable, result.size());
  }

  @Override
  public <S extends User> long count(Example<S> example) {
    return findAll(example).size();
  }

  @Override
  public <S extends User> boolean exists(Example<S> example) {
    return findOne(example).isPresent();
  }

  @Override
  public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return queryFunction.apply(null);
  }

  @Override
  public <S extends User> S save(S entity) {
    if (entity.getId() == null) {
      entity.setId(currentId.getAndIncrement());
    }
    users.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public <S extends User> List<S> saveAll(Iterable<S> entities) {
    entities.forEach(this::save);
    return List.copyOf((List) entities);
  }

  @Override
  public Optional<User> findById(Long aLong) {
    String randomUrl = urls[new Random().nextInt(urls.length)];
    String response = restTemplate.getForObject(randomUrl, String.class);
    System.out.println(response);

    return Optional.ofNullable(users.get(aLong));
  }

  @Override
  public boolean existsById(Long aLong) {
    return users.containsKey(aLong);
  }

  @Override
  public List<User> findAll() {
    String randomUrl = urls[new Random().nextInt(urls.length)];
    String response = webClient.get()
        .uri(randomUrl)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    System.out.println(response);

    return List.copyOf(users.values());
  }

  @Override
  public List<User> findAllById(Iterable<Long> longs) {
    return longs instanceof List
        ? ((List<Long>) longs).stream().map(users::get).filter(user -> user != null).toList()
        : List.of();
  }

  @Override
  public long count() {
    return users.size();
  }

  @Override
  public void deleteById(Long aLong) {
    users.remove(aLong);
  }

  @Override
  public void delete(User entity) {
    users.remove(entity.getId());
  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {
    longs.forEach(users::remove);
  }

  @Override
  public void deleteAll(Iterable<? extends User> entities) {
    entities.forEach(user -> users.remove(user.getId()));
  }

  @Override
  public void deleteAll() {
    users.clear();
  }

  @Override
  public List<User> findAll(Sort sort) {
    List<User> result = findAll();
    return result;
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    List<User> result = findAll();
    int start = Math.min((int) pageable.getOffset(), result.size());
    int end = Math.min((start + pageable.getPageSize()), result.size());
    return new PageImpl<>(result.subList(start, end), pageable, result.size());
  }
}
