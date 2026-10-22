package com.craft;

import jakarta.persistence.*;
import java.util.List;

public class App {

    public static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("studentPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE: Save a student
    public static void createStudent(Student student) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(student);
            tx.commit();
        } finally {
            em.close();
        }
    }

    // READ: Find a student by ID
    public static Student getStudentById(int id) {
        EntityManager em = getEntityManager();

        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    // UPDATE: Change a student's name
    public static void updateStudentName(String newName, int id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, id);

            if (student != null) {
                student.setName(newName);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // UPDATE: Change a student's name and technology
    public static void updateStudentNameAndTech(
            String newName, String newTech, int id) {

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, id);

            if (student != null) {
                student.setName(newName);
                student.setTech(newTech);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // DELETE: Remove a student by ID
    public static void deleteStudentById(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Student student = em.find(Student.class, id);

            if (student != null) {
                em.remove(student);
            }

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // JPQL 1: Select all students
    public static List<Student> getAllStudents() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Student> query =
                    em.createQuery("SELECT s FROM Student s", Student.class);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // JPQL 2: Select students by name
    public static List<Student> getStudentsByName(String name) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.name = :name",
                    Student.class);

            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // JPQL 3: Select students by technology
    public static List<Student> getStudentsByTech(String tech) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.tech = :tech",
                    Student.class);

            query.setParameter("tech", tech);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // JPQL 4: Select students whose names contain a word
    public static List<Student> getStudentsByNameContaining(
            String substring) {

        EntityManager em = getEntityManager();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.name LIKE :pattern",
                    Student.class);

            query.setParameter("pattern", "%" + substring + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // JPQL 5: Update a student's technology
    public static int updateStudentTech(int id, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Query query = em.createQuery(
                    "UPDATE Student s SET s.tech = :newTech WHERE s.id = :id");

            query.setParameter("newTech", newTech);
            query.setParameter("id", id);

            int updatedCount = query.executeUpdate();
            tx.commit();

            return updatedCount;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // JPQL 6: Delete a student by ID
    public static int deleteStudentByIdJPQL(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Query query = em.createQuery(
                    "DELETE FROM Student s WHERE s.id = :id");

            query.setParameter("id", id);

            int deletedCount = query.executeUpdate();
            tx.commit();

            return deletedCount;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // MAIN: Test the lab operations
    public static void main(String[] args) {

        // Display all students
        List<Student> students = getAllStudents();

        System.out.println("All students:");
        students.forEach(System.out::println);

        // Display students who use Java
        System.out.println("Students using Java:");
        getStudentsByTech("Java").forEach(System.out::println);

        // Display student with ID 1
        System.out.println("Student with ID 1:");
        System.out.println(getStudentById(1));

        emf.close();
    }
}