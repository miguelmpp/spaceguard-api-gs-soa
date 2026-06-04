package br.com.fiap.spaceguard.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<Map<String, Object>> verificarStatusApi() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "aplicacao", "SpaceGuard API",
                "mensagem", "API de monitoramento espacial em funcionamento",
                "dataHora", LocalDateTime.now()
        ));
    }
}