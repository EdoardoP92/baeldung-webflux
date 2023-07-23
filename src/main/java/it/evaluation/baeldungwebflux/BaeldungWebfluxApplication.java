package it.evaluation.baeldungwebflux;

import it.evaluation.baeldungwebflux.model.Foo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class BaeldungWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaeldungWebfluxApplication.class, args);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Foo> publishFoo(){
		var interval = Flux.interval(Duration.ofSeconds(1));
		var fooStream = Flux.fromStream(Stream.generate(()->{
			long id = Math.abs(new Random().nextLong());
			return new Foo(id, "foo_"+id);
		}));
		return Flux.zip(interval, fooStream).map(Tuple2::getT2);
	}
}
