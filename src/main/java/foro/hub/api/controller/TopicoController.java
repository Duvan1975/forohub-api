package foro.hub.api.controller;

import foro.hub.api.domain.topico.*;
import foro.hub.api.infra.errores.TratadorDeErrores;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> RegistrarTopico(
            @RequestBody @Valid DatosResgistroTopico datosResgistroTopico,
            UriComponentsBuilder uriComponentsBuilder){

        if (topicoRepository.existsByTituloAndMensaje(datosResgistroTopico.titulo(), datosResgistroTopico.mensaje())){
            throw new TratadorDeErrores.DuplicatedTopicException("Ya existe un tópico con el mismo título y mensaje");
        }

        Topico topico = topicoRepository.save(new Topico(datosResgistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().toString(),
                topico.getAutor(),
                topico.getCurso()
        );
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }
    @GetMapping
    public Page<DatosListadoTopico> listadoTopicos(
            @PageableDefault(page = 0, size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion){
        return topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizacionTopicos(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

        // Verificar si el ID existe
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico no encontrado con ID: " + id);
        }
        // Verificar si ya existe un tópico con el mismo mensaje, excluyendo el propio tópico
        boolean existeTopicoDuplicado = topicoRepository.existsByMensajeAndIdNot(
                datosActualizarTopico.mensaje(),
                id
        );

        if (existeTopicoDuplicado) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo mensaje.");
        }
        // Actualizar el tópico
        Topico topico = topicoRepository.getReferenceById(id);

        topico.actualizarDatos(datosActualizarTopico);

        // Devolver la respuesta
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().toString(),
                topico.getAutor(),
                topico.getCurso()
        );

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(null); // Devuelve un 400 si el ID es inválido.
        }

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));

        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().toString(),
                topico.getAutor(),
                topico.getCurso()
        );
        return ResponseEntity.ok(datosTopico);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosListadoTopico>> buscarPorCurso(
            @RequestParam String curso,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        var resultados = topicoRepository.findByCurso(curso, paginacion);
        return ResponseEntity.ok(resultados.map(DatosListadoTopico::new));
    }
}
