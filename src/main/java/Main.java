import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String args[]){
        List<String> values1 = Arrays.asList("one", "two", "three", "four");
        List<String> values2 = Arrays.asList("five", "six", "seven", "eight");

        List<List<String>> allVals = Arrays.asList(values1, values2);
        List<String> allValsPlain = new ArrayList<>();
        allVals.stream().reduce(allValsPlain, (x, y) -> {x.addAll(y); return x;});


        allValsPlain.forEach(x->System.out.println(x));
        filterAndForEach(allValsPlain);
        List<SampleObject> sampleObjects = mapAndCollect(allValsPlain);
        mapToIntAndSum(sampleObjects);
        flatMapAndOptional(sampleObjects);
        peekAndSkip(allValsPlain);
    }

    public static void filterAndForEach(List<String> values){
        System.out.println("---");
       values.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String number) {
                return !number.equals("five");
            }
        })
       .forEach(System.out::println);

    }

    public static List<SampleObject> mapAndCollect(List<String> values){
        System.out.println("---");
        values.stream()
                .filter(x -> !x.equals("three"))
                .map(name -> {
                    SampleObject sampleObject = new SampleObject(name, values.indexOf(name) + 1);
                    return sampleObject;
                })
                .forEach(System.out::println);

        List<SampleObject> sampleObjects = values.stream()
                .filter(x -> !x.equals("three"))
                .map(name -> {
                    SampleObject sampleObject = new SampleObject(name, values.indexOf(name) + 1);
                    return sampleObject;
                })
                .collect(Collectors.toList());
        sampleObjects.forEach(System.out::println);
        return sampleObjects;
    }

    public static void mapToIntAndSum(List<SampleObject> values){
        System.out.println("---");
        Integer sum = values.stream().mapToInt(SampleObject::getNumber).sum();
        System.out.println(sum);
    }

    public static void flatMapAndOptional(List<SampleObject> values){
        System.out.println("---");
        values.forEach(x ->{
            Integer size = new Random().nextInt(10);
            List<Integer> listOfIntegers = new ArrayList<>();
            while(size > 0){
                listOfIntegers.add(new Random().nextInt(10));
                size--;
            }
            x.setRandomList(listOfIntegers);
        });

        Optional <Integer> integerOptional = values.stream()
                .map(sampleObject -> sampleObject.getRandomList().stream())
                .flatMap(integerStream -> integerStream.filter(number -> number.equals(3)))
                        .findAny();
        try {
            integerOptional.ifPresent(System.out::println);
        }
        finally {
            System.out.println("Found number 3 probably");
        }
    }

    public static void peekAndSkip(List<String> values){
        System.out.println("---");

        List<String> valueFiltered = values.stream().skip(1).filter(val -> !val.equals("three"))
                .peek(obj -> System.out.print("peeked: " + obj + "; "))
                .collect(Collectors.toList());
    }

    static class SampleObject{
        private String name;
        private Integer number;
        private List<Integer> randomList;

        public SampleObject(String name, Integer number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public List<Integer> getRandomList() {
            return randomList;
        }

        public void setRandomList(List<Integer> randomList) {
            this.randomList = randomList;
        }

        @Override
        public String toString() {
            return "SampleObject{" +
                    "name='" + name + '\'' +
                    ", number=" + number +
                    '}';
        }
    }
}
