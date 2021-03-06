package demo.api.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@RestController
class CalcController {

    /**
     * <pre>
     * {@code
     * curl -s http://localhost:8080/api/calc\?x\=10\&y\=9 | jq .
     * }
     * </pre>
     *
     * @param x
     * @param y
     * @param op
     * @return
     * @throws Exception
     */
    @GetMapping("/api/calc")
    public Object calc(BigDecimal x, BigDecimal y, @RequestParam(defaultValue = "PLUS") Operator op) throws Exception {

        String result = op.apply(x, y).toPlainString();

        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        return Map.of( //
                "result", result, //
                "node", hostAddress //
        );

    }

    @RequiredArgsConstructor
    enum Operator implements BiFunction<BigDecimal, BigDecimal, BigDecimal> {

        PLUS(BigDecimal::add),

        MINUS(BigDecimal::subtract),

        DIV(BigDecimal::divide),

        MUL(BigDecimal::multiply),
        ;

        private final BiFunction<BigDecimal, BigDecimal, BigDecimal> operator;

        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            return operator.apply(a, b);
        }
    }
}
