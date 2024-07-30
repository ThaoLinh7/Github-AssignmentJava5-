package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.repository.CustomerRepository;
import edu.poly.shop.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		
	}

	public List<Customer> findByNameContaining(String name) {
		return customerRepository.findByNameContaining(name);
	}

	public Page<Customer> findByNameContaining(String name, Pageable pageable) {
		return customerRepository.findByNameContaining(name, pageable);
	}

	public <S extends Customer> S save(S entity) {
		return customerRepository.save(entity);
	}

	public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
		return customerRepository.saveAll(entities);
	}

	public <S extends Customer> Optional<S> findOne(Example<S> example) {
		return customerRepository.findOne(example);
	}

	public List<Customer> findAll(Sort sort) {
		return customerRepository.findAll(sort);
	}

	public void flush() {
		customerRepository.flush();
	}

	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	public <S extends Customer> S saveAndFlush(S entity) {
		return customerRepository.saveAndFlush(entity);
	}

	public <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities) {
		return customerRepository.saveAllAndFlush(entities);
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public List<Customer> findAllById2(Iterable<Long> ids) {
		return customerRepository.findAllById(ids);
	}

	public void deleteInBatch(Iterable<Customer> entities) {
		customerRepository.deleteInBatch(entities);
	}

	public <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable) {
		return customerRepository.findAll(example, pageable);
	}

	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	public void deleteAllInBatch(Iterable<Customer> entities) {
		customerRepository.deleteAllInBatch(entities);
	}

	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	public <S extends Customer> long count(Example<S> example) {
		return customerRepository.count(example);
	}

	public void deleteAllByIdInBatch2(Iterable<Long> ids) {
		customerRepository.deleteAllByIdInBatch(ids);
	}

	public <S extends Customer> boolean exists(Example<S> example) {
		return customerRepository.exists(example);
	}

	public void deleteAllInBatch() {
		customerRepository.deleteAllInBatch();
	}

	public Customer getOne(Long id) {
		return customerRepository.getOne(id);
	}

	public <S extends Customer, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return customerRepository.findBy(example, queryFunction);
	}

	public long count() {
		return customerRepository.count();
	}

	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	public Customer getById(Long id) {
		return customerRepository.getById(id);
	}

	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	public void deleteAllById2(Iterable<? extends Long> ids) {
		customerRepository.deleteAllById(ids);
	}

	public Customer getReferenceById(Long id) {
		return customerRepository.getReferenceById(id);
	}

	public void deleteAll(Iterable<? extends Customer> entities) {
		customerRepository.deleteAll(entities);
	}

	public <S extends Customer> List<S> findAll(Example<S> example) {
		return customerRepository.findAll(example);
	}

	public <S extends Customer> List<S> findAll(Example<S> example, Sort sort) {
		return customerRepository.findAll(example, sort);
	}

	public void deleteAll() {
		customerRepository.deleteAll();
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Customer> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
