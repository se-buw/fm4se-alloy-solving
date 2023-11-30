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

Implement the constraints of the Deadbury Mansion puyzzle in Alloy.
* The signatures are all created for you.
* The relations are already defined as fields in the signature `Person`.

$$
\begin{equation}
\begin{aligned}
  \exists x \cdot killed(x, Agatha) ... (1)  \\ 
  \forall x \cdot \forall y \cdot killed(x,y) \Rightarrow (hates(x,y)\wedge\neg richer(x,y)) ... (2) \\ 
  \forall x \cdot hates(Agatha, x) \Rightarrow \neg hates(Charles, x) ... (3) \\
  hates(Agatha, Agatha) \wedge hates(Agatha, Charles) ... (4) \\
  \forall x \cdot \neg richer(x, Agatha) \Rightarrow hates(Butler, x) ... (5) \\
  \forall x \cdot hates(Agatha, x) \Rightarrow hates(Butler, x) ... (6) \\
  \forall x \cdot \exists y \cdot \neg hates(x,y) ... (7)
\end{aligned}
\end{equation}
$$


Start from this **[Template](https://play.formal-methods.net/?check=ALS&p=poncho-wanted-single-legume)**.

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/alloy/Tasks.java (task_2)](src/main/java/de/buw/fm4se/alloy/Tasks.java)

## Task 3

Implement the missing predicates in this Alloy model. The model describes a simple file system and the deletion policies of files.

Start from this **[model](https://alloy.formal-methods.net/?check=ALS&p=thirty-fog-cackle-jacket)** and complete the predicates `inv1` to `inv10`.

**Submission:** Submit the permalink in [src/main/java/de/buw/fm4se/alloy/Tasks.java (task_3)](src/main/java/de/buw/fm4se/alloy/Tasks.java)