---
id: BR001
domain: roteiro
layer: core
priority: high
status: active
related: []
created: 2024-12-28
updated: 2024-12-28
---

# BR001: Duração Mínima de Permanência em Destino

## Regra de Negócio

Todo destino no roteiro deve ter uma permanência mínima baseada no tipo de destino para garantir experiência de qualidade ao viajante.

## Especificação BDD

### Feature: Validação de duração mínima em destino

**Como** sistema de criação de roteiros  
**Quero** validar a duração mínima de permanência em cada destino  
**Para** garantir que o viajante tenha tempo suficiente para aproveitar o local

---

### Cenário 1: Destino capital com duração adequada

**Dado** um roteiro com destino "São Paulo" do tipo CAPITAL  
**E** a permanência planejada é de 3 dias  
**Quando** o sistema valida o roteiro  
**Então** a duração deve ser aceita  
**E** o destino deve ser adicionado ao roteiro

### Cenário 2: Destino capital com duração insuficiente

**Dado** um roteiro com destino "Rio de Janeiro" do tipo CAPITAL  
**E** a permanência planejada é de 1 dia  
**Quando** o sistema valida o roteiro  
**Então** deve retornar erro "DURACAO_MINIMA_NAO_ATENDIDA"  
**E** deve informar "Capitais requerem no mínimo 2 dias de permanência"  
**E** o destino NÃO deve ser adicionado ao roteiro

### Cenário 3: Destino praia com duração adequada

**Dado** um roteiro com destino "Florianópolis" do tipo PRAIA  
**E** a permanência planejada é de 4 dias  
**Quando** o sistema valida o roteiro  
**Então** a duração deve ser aceita  
**E** o destino deve ser adicionado ao roteiro

### Cenário 4: Diferentes tipos de destino e suas durações mínimas

| Tipo Destino | Duração Mínima | Duração Testada | Resultado Esperado |
|--------------|----------------|-----------------|-------------------|
| CAPITAL      | 2 dias         | 3 dias          | ✅ ACEITO         |
| CAPITAL      | 2 dias         | 1 dia           | ❌ REJEITADO      |
| PRAIA        | 3 dias         | 4 dias          | ✅ ACEITO         |
| PRAIA        | 3 dias         | 2 dias          | ❌ REJEITADO      |
| HISTORICO    | 1 dia          | 2 dias          | ✅ ACEITO         |
| NATUREZA     | 2 dias         | 1 dia           | ❌ REJEITADO      |

##  Regras de Duração

```
CAPITAL:   mínimo 2 dias
PRAIA:     mínimo 3 dias
HISTORICO: mínimo 1 dia
NATUREZA:  mínimo 2 dias
RURAL:     mínimo 2 dias
```

## Implementação

### Camada de Domínio (Hexagonal)
- **Agregado**: `domain.roteiro.Roteiro`
- **Entidade**: `domain.roteiro.Destino`
- **Value Object**: `domain.roteiro.Permanencia`
- **Enum**: `domain.roteiro.TipoDestino`
- **Validador**: `domain.roteiro.PermanenciaValidator`

### Estrutura
```
domain/
  roteiro/
    Roteiro.java              # Agregado raiz
    Destino.java              # Entidade
    Permanencia.java          # Value Object
    TipoDestino.java          # Enum
    PermanenciaValidator.java # Validador
```

## Testes Relacionados

```java
// Mapeamento Cenário → Teste
DestinoTest.deveAceitarCapitalComDuracaoAdequada()        // Cenário 1
DestinoTest.deveRejeitarCapitalComDuracaoInsuficiente()   // Cenário 2
DestinoTest.deveAceitarPraiaComDuracaoAdequada()          // Cenário 3
DestinoTest.deveValidarDuracaoMinimaPorTipoDestino()      // Cenário 4
```

## ⚠️ Exceções

```java
DuracaoMinimaException
  - código: "DURACAO_MINIMA_NAO_ATENDIDA"
  - mensagem: "{TipoDestino} requer no mínimo {X} dias de permanência"
  - httpStatus: 400 (Bad Request)
```

##  Relacionamentos

- **Depende de**: Nenhuma outra BR
- **É usado por**: BR005 (Validação de roteiro completo)

##  Notas para IA

```
Contexto: Sistema de planejamento de viagens
Objetivo: Garantir qualidade da experiência do viajante
Abordagem: Validação no momento da adição do destino ao roteiro
Falha: Retorna exceção de domínio que será tratada na camada de aplicação
```

##  Referências

- [Documentação expandida na Wiki](link-wiki)
- [Issue #XX no GitHub Project](link-issue)
- [Código fonte - Destino.java](link-codigo)

##  Histórico

| Data       | Versão | Alteração                    | Autor |
|------------|--------|------------------------------|-------|
| 2024-12-28 | 1.0    | Criação inicial da regra     | Time  |