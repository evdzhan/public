package labdas_test;

/**
 * By Evdzhan Mustafa. Created on 17/02/16.
 */
public class LambdaTester_02 {
    public static void main(String args[]) {
        makeItTalk(() -> "quack");
        makeItTalk(() -> "bark");
        makeItTalk(() -> "meow");
        makeItTalk(() -> "Hello! I am a human. I can talk.");
    }

    static void makeItTalk(Audible<String> s) {
        System.out.println(s.makeSound());
    }


    interface Audible<T> {
        T makeSound();
    }
}
