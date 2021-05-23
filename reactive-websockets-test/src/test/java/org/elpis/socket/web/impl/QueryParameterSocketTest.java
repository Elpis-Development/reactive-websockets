package org.elpis.socket.web.impl;

import org.elpis.socket.web.BaseWebSocketTest;
import org.elpis.socket.web.context.BootStarter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BootStarter.class)
public class QueryParameterSocketTest extends BaseWebSocketTest {

    @MockBean
    private WebSocketService webSocketService;

    @BeforeEach
    public void each() {
        doAnswer(answer -> {
            final ServerWebExchange serverWebExchange = answer.getArgument(0);
            final WebSocketHandler webSocketHandler = answer.getArgument(1);

            return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy())
                    .handleRequest(serverWebExchange, webSocketHandler);
        }).when(webSocketService).handleRequest(any(ServerWebExchange.class), any(WebSocketHandler.class));
    }

    @Test
    public void getWithStringQueryTest() throws Exception {
        //given
        final String data = this.randomTextString(5);

        final String path = "/query/single/get/string?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"" + data + "\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithStringQueryNoValueTest(final CapturedOutput output) throws Exception {
        //given
        final String data = this.randomTextString(5);

        final String path = "/query/single/get/no/string?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono().timeout(DEFAULT_FAST_TEST_FALLBACK))
                .verifyError(TimeoutException.class);

        assertThat(output)
                .contains("Request parameter `@SocketQueryParam ids` at method `getWithNoStringQuery()` was marked as " +
                        "`required` but was not found on request");
    }

    @Test
    public void getWithNumericByteQueryTest() throws Exception {
        //given
        final byte data = this.getRandomByte();

        final String path = "/query/single/get/numeric/byte?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericPrimitiveByteQueryTest() throws Exception {
        //given
        final byte data = this.getRandomByte();

        final String path = "/query/single/get/numeric/primitive/byte?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericShortQueryTest() throws Exception {
        //given
        final short data = this.getRandomShort();

        final String path = "/query/single/get/numeric/short?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericShortPrimitiveQueryTest() throws Exception {
        //given
        final short data = this.getRandomShort();

        final String path = "/query/single/get/numeric/primitive/short?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericIntQueryTest() throws Exception {
        //given
        final int data = this.getRandomInteger();

        final String path = "/query/single/get/numeric/int?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericIntPrimitiveQueryTest() throws Exception {
        //given
        final int data = this.getRandomInteger();

        final String path = "/query/single/get/numeric/primitive/int?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericLongQueryTest() throws Exception {
        //given
        final long data = this.getRandomLong();

        final String path = "/query/single/get/numeric/long?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericLongPrimitiveQueryTest() throws Exception {
        //given
        final long data = this.getRandomLong();

        final String path = "/query/single/get/numeric/primitive/long?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithBooleanQueryTest() throws Exception {
        //given
        final boolean data = true;

        final String path = "/query/single/get/boolean?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithBooleanPrimitiveQueryTest() throws Exception {
        //given
        final boolean data = false;

        final String path = "/query/single/get/boolean/primitive?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericFloatQueryTest() throws Exception {
        //given
        final float data = this.getRandomFloat();

        final String path = "/query/single/get/numeric/float?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericFloatPrimitiveQueryTest() throws Exception {
        //given
        final float data = this.getRandomFloat();

        final String path = "/query/single/get/numeric/primitive/float?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericDoubleQueryTest() throws Exception {
        //given
        final double data = this.getRandomDouble();

        final String path = "/query/single/get/numeric/double?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithNumericDoublePrimitiveQueryTest() throws Exception {
        //given
        final float data = getRandomFloat();

        final String path = "/query/single/get/numeric/primitive/double?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithCharacterWrapperQueryTest() throws Exception {
        //given
        final String data = this.randomTextString(5);

        final String path = "/query/single/get/char?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"" + data.charAt(0) + "\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithCharPrimitiveQueryTest() throws Exception {
        //given
        final String data = this.randomTextString(5);

        final String path = "/query/single/get/primitive/char?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"" + data.charAt(0) + "\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithBigIntegerQueryTest() throws Exception {
        //given
        final int data = this.getRandomInteger();

        final String path = "/query/single/get/bigint?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithBigDecimalQueryTest() throws Exception {
        //given
        final float data = this.getRandomFloat();

        final String path = "/query/single/get/bigdeci?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":" + data + "}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getEnumQueryTest() throws Exception {
        //given
        final BootStarter.Test data = BootStarter.Test.VOID;

        final String path = "/query/single/get/enum?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"" + data + "\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getWithStringQueryNoRequiredTest() throws Exception {
        //given
        final String data = this.randomTextString(10);

        final String path = "/query/single/get/no/required?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"null\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getListQueryTest() throws Exception {
        //given
        final List<String> data = List.of(this.randomTextString(3), this.randomTextString(3), this.randomTextString(3));

        final String path = "/query/single/get/list?ids=" + String.join(",", data);
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"" + data.toString().replaceAll(" ", "") + "\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }

    @Test
    public void getDefaultQueryTest() throws Exception {
        //given
        final String data = this.randomTextString(10);

        final String path = "/query/single/get/default?id=" + data;
        final Sinks.One<String> sink = Sinks.one();

        //expected
        final String expected = "{\"query\":\"default\"}";

        //test
        this.withClient(path, (session) -> session.receive().map(WebSocketMessage::getPayloadAsText)
                .log()
                .doOnNext(v -> sink.tryEmitValue(v.replaceAll(" ", "")))
                .then()).subscribe();

        //verify
        StepVerifier.create(sink.asMono())
                .expectNext(expected)
                .expectComplete()
                .log()
                .verify(DEFAULT_GENERIC_TEST_FALLBACK);
    }
}
