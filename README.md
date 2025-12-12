#### 💯Points: ![Points bar](../../blob/badges/.github/badges/points-bar.svg)

#### 📝 [Report](../../blob/badges/report.md)
---

# Alloy: Manual Encoding

This project and worksheet is part of the lecture Formal Methods for Software Engineering. 

## Task 1

Create an Alloy model for a scenario of your choice. The senario must make sense, i.e., not a `sig A` ... `sig B` example, and it needs to be different from the examples in the lecture.
* Declare at least 4 signatures each with at least 2 fields. 
* Use inheritance between signatures at least once.
* Define at least 2 facts and 2 predicates.
* Add two run commands to your model.
  * The first run command should be unsatisfiable.
  * The second run command should be satisfiable and return at least 2 instances.

Start from this **[Template](https://play.formal-methods.net/?check=ALS&p=lumber-flinch-dart-relic)**.



**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/alloy/Tasks.java (task_1)](src/main/java/de/buw/fm4se/alloy/Tasks.java)

## Task 2

Implement the constraints of the following IT security puzzle in Alloy. There are three administrators working at a data center. One of them is a bad actor. Find out which one.
* The signature for all `Admin`s and for the `BadActor` are already created for you.
* Missing relations should be defined inside siganture `Admin`. 

Constraints:
1. Admins have security levels higher than other admins. 
2. Security levels form an irreflexive and transitive relationship between admins.
3. No two admins have the same security level.
4. Admins may trust other admins.
5. Everybody trusts at least one admin.
6. No admin trusts an admin with a lower security level.
7. Alice trusts at most those who trusts her.
8. Bob trusts only those who have a higher security level than Charlie.
9. Charlie trusts no one who trusts Bob.
10. The bad actor trusts only those who do not trust Alice.


Start from this **[Template](https://play.formal-methods.net/?check=ALS&p=balmy-squall-speed-driven)**.

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/alloy/Tasks.java (task_2)](src/main/java/de/buw/fm4se/alloy/Tasks.java)

## Task 3

Implement the missing predicates in this Alloy model. The model describes a simple file system and the deletion policies of files.

Start from this **[model](https://play.formal-methods.net/?check=ALS&p=thirty-fog-cackle-jacket)** and complete the predicates `inv1` to `inv10`.

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/alloy/Tasks.java (task_3)](src/main/java/de/buw/fm4se/alloy/Tasks.java)