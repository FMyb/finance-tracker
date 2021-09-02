package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.data.Type;

import javax.persistence.*;

/**
 * @author Natalia Nikonova
 */
@Entity
@Table(name = "categories")
@SequenceGenerator(allocationSize = 1, name = "category_seq", sequenceName = "category_seq")
public class CategoryEntity {
   @Id
   @GeneratedValue(generator = "category_seq")
   @Column(name = "id")
   private long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserEntity user;
   @Column(name = "name")
   private String name;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "type")
   private Type type;

   public CategoryEntity() {

   }

   public CategoryEntity(UserEntity user, String name, Type type) {
      this.user = user;
      this.name = name;
      this.type = type;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public UserEntity getUser() {
      return user;
   }

   public void setUser(UserEntity user) {
      this.user = user;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }
}
