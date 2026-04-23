# Informe de Refactorización y Solución de Violaciones

Este documento detalla las violaciones arquitectónicas y de Clean Code identificadas en el proyecto original y explica la solución aplicada para cada una.

## 1. Arquitectura Hexagonal e Inmutabilidad

### Violación: Objetos de Dominio Mutables
**Problema:** Se utilizaba la anotación `@Data` de Lombok en clases como `UserModel` y DTOs de respuesta. Esto permitía modificar los datos del usuario en cualquier punto del flujo, rompiendo la integridad del dominio.
**Solución:** Se transformaron los modelos y DTOs a estructuras inmutables utilizando `record` (Java 14+) o la anotación `@Value` de Lombok. Esto garantiza que una vez creado el objeto, su estado no pueda ser alterado.

### Violación: Acoplamiento de Infraestructura en el Dominio
**Problema:** El modelo de dominio `UserModel` contenía importaciones de `UserEntity` (infraestructura) y métodos de conversión hacia la base de datos (`toEntity`). Esto viola el principio fundamental de la arquitectura hexagonal donde el dominio debe ser agnóstico a la tecnología.
**Solución:** Se eliminaron todas las referencias a clases de infraestructura del dominio. La lógica de mapeo se centralizó en clases específicas denominadas mappers dentro de la capa de infraestructura.

---

## 2. Naming y Estándares de Codificación

### Violación: Uso de Abreviaturas Inexpresivas
**Problema:** Se utilizaban nombres de variables abreviados como `opt`, `usrs`, `pw` y `upd`. Estos nombres dificultan la lectura y obligan al desarrollador a "descifrar" el código.
**Solución:** Se aplicó la regla de nombres descriptivos. Las variables fueron renombradas a términos claros y completos como `option`, `users`, `password` y `response`, mejorando significativamente la legibilidad del código.

### Violación: Comparaciones Manuales de Nulidad
**Problema:** El código estaba plagado de comparaciones `if (objeto == null)`. Esta es una práctica propensa a errores y menos expresiva que las alternativas modernas.
**Solución:** Se estandarizó el uso de la clase utilitaria `java.util.Objects`. Se emplearon métodos como `Objects.requireNonNull()` para fallos tempranos y `Objects.isNull()` para comprobaciones lógicas, lo que hace el código más robusto y legible.

### Violación: Importaciones con Comodines
**Problema:** Se utilizaban importaciones como `import java.sql.*`, lo cual oculta las dependencias reales de la clase y puede generar conflictos de nombres.
**Solución:** Se reemplazaron todos los comodines por importaciones específicas. Ahora es posible ver exactamente qué clases utiliza cada componente.

---

## 3. Clean Code y Diseño de Software

### Violación: Incumplimiento de la Ley de Deméter
**Problema:** Las clases externas accedían profundamente a la estructura de los objetos (ej. `usuario.getId().value()`). Esto crea un acoplamiento excesivo con la estructura interna de los *Value Objects*.
**Solución:** Se implementaron métodos de delegación en `UserModel` (como `idValue()`, `emailValue()`, etc.). Ahora las capas superiores interactúan solo con el modelo de dominio sin conocer cómo están construidos sus objetos internos.

### Violación: Violación de CQS (Command Query Separation)
**Problema:** Existían métodos que realizaban una acción de cambio de estado (Comando) y al mismo tiempo devolvían un resultado de consulta (Consulta), o viceversa.
**Solución:** Se separaron las responsabilidades. Los métodos de "ejecución" ahora se enfocan en realizar la acción, mientras que la recuperación de datos se gestiona de forma independiente o mediante flujos de retorno claros que no mezclan efectos secundarios.

### Violación: Baja Cohesión en Clases "Utils"
**Problema:** Existía una clase `UserValidationUtils` que agrupaba validaciones de email, password, roles y estados. Esto es una señal de lógica mal ubicada y falta de cohesión.
**Solución:** Se eliminó la clase utilitaria y se movió la lógica a donde pertenece: las validaciones de formato al Value Object correspondiente y las reglas de negocio al modelo `UserModel`.

### Violación: Acoplamiento Temporal
**Problema:** Algunas clases requerían que se llamara a un método `init()` antes de poder usarlas. Si el programador olvidaba este paso, el sistema fallaba en tiempo de ejecución de forma impredecible.
**Solución:** Se eliminó la necesidad de inicialización manual. El diseño ahora asegura que los objetos estén en un estado válido y listos para operar inmediatamente después de ser instanciados mediante su constructor.

---

## 4. Seguridad y Gestión de Errores

### Violación: Exposición de PII en Logs
**Problema:** Los logs del sistema registraban información sensible (PII) como el email y la contraseña de los usuarios durante los procesos de creación y actualización.
**Solución:** Se limpiaron todos los logs. La información sensible fue eliminada o reemplazada por identificadores no sensibles (como el ID de usuario), cumpliendo con las mejores prácticas de seguridad y protección de datos.

### Violación: Captura Genérica y Silenciosa de Excepciones
**Problema:** Se utilizaban bloques `try-catch` que capturaban excepciones solo para imprimirlas o relanzarlas sin aportar información adicional, a veces ocultando la causa raíz.
**Solución:** Se eliminaron las capturas redundantes. Se permitió que las excepciones fluyan hacia un manejador global de errores, asegurando que el sistema responda de manera consistente ante fallos y que la traza del error no se pierda.
