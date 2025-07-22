/**
 * Archivo JavaScript Principal para la Aplicación Web de la EPS.
 * Versión final y funcional con CRUD completo para TODAS las entidades.
 */
document.addEventListener('DOMContentLoaded', function () {

    // ===================================================================
    // SECCIÓN 0: POBLACIÓN DE SELECTS EN EL ADMIN
    // ===================================================================
    function populateAdminSelects() {
        const form = document.getElementById('entityForm');
        if (!form) return;

        const selectsToPopulate = [
            { selector: '[name="idPlanSalud"]', endpoint: '/api/planes-salud', valueField: 'idPlanSalud', textField: 'nombrePlan' },
            { selector: '[name="idEspecialidad"]', endpoint: '/api/especialidades', valueField: 'idEspecialidad', textField: 'nombreEspecialidad' },
            { selector: '[name="idCentroSalud"]', endpoint: '/api/centros_salud', valueField: 'idCentroSalud', textField: 'nombre' }
        ];

        selectsToPopulate.forEach(config => {
            const selectElement = form.querySelector(config.selector);
            if (selectElement) {
                fetch(config.endpoint)
                    .then(response => response.json())
                    .then(data => {
                        selectElement.innerHTML = '<option selected disabled value="">Seleccione una opción...</option>';
                        data.forEach(item => {
                            const option = document.createElement('option');
                            option.value = item[config.valueField];
                            option.textContent = item[config.textField];
                            selectElement.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error(`Error al poblar el select ${config.selector}:`, error);
                        selectElement.innerHTML = '<option selected disabled value="">Error al cargar datos</option>';
                    });
            }
        });
    }

    if (window.location.pathname.startsWith('/admin/')) {
        populateAdminSelects();
    }

    // ===================================================================
    // SECCIÓN 1: LOGIN (PARA TODOS LOS PORTALES)
    // ===================================================================
    function handleLogin(formId, roleToVerify, redirectUrl) {
        const loginForm = document.getElementById(formId);
        if (loginForm) {
            loginForm.addEventListener('submit', function (event) {
                event.preventDefault();
                const submitButton = loginForm.querySelector('button[type="submit"]');
                const errorDiv = document.getElementById('login-error-message');
                if (errorDiv) errorDiv.classList.add('d-none');
                submitButton.disabled = true;
                submitButton.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Ingresando...`;

                const loginData = {
                    login: loginForm.querySelector('[name="login"]').value,
                    contrasena: loginForm.querySelector('[name="contrasena"]').value
                };

                fetch('/api/usuarios/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(loginData)
                })
                    .then(response => {
                        if (response.ok) return response.json();
                        return response.text().then(text => { throw new Error(text || 'Usuario o contraseña incorrectos.') });
                    })
                    .then(data => {
                        if (data.rol && data.rol.nombreRol === roleToVerify) {
                            sessionStorage.setItem('usuarioLogueado', JSON.stringify(data));
                            window.location.href = redirectUrl;
                        } else {
                            throw new Error('Acceso no autorizado para este portal.');
                        }
                    })
                    .catch(error => {
                        if (errorDiv) {
                            errorDiv.textContent = error.message;
                            errorDiv.classList.remove('d-none');
                        } else {
                            alert(error.message);
                        }
                        submitButton.disabled = false;
                        submitButton.textContent = 'Ingresar';
                    });
            });
        }
    }

    handleLogin('form-login-afiliado', 'Afiliado', '/afiliado/dashboard');
    handleLogin('form-login-profesional', 'Profesional', '/profesional/dashboard');
    handleLogin('form-login-admin', 'Administrativo', '/admin/dashboard');

    // ===================================================================
    // SECCIÓN 2: REGISTRO PÚBLICO DE AFILIADOS
    // ===================================================================
    const registroForm = document.getElementById('form-registro');
    if (registroForm) {
        registroForm.addEventListener('submit', async function (event) {
            event.preventDefault();
            const submitButton = registroForm.querySelector('button[type="submit"]');
            const originalButtonText = submitButton.innerHTML;
            submitButton.disabled = true;
            submitButton.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Registrando...`;
            const formData = new FormData(registroForm);
            const fullData = Object.fromEntries(formData.entries());

            try {
                const terceroData = {
                    tipoDocumento: fullData.tipo_documento,
                    numeroDocumento: fullData.numero_documento,
                    nombre: fullData.nombre,
                    apellido: fullData.apellido,
                    telefono: fullData.telefono,
                    correo: fullData.correo
                };
                const responseTercero = await fetch('/api/terceros', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(terceroData)
                });
                if (!responseTercero.ok) {
                    const errorText = await responseTercero.text();
                    throw new Error(`Error al crear datos personales: ${errorText}`);
                }
                const terceroGuardado = await responseTercero.json();
                const idTercero = terceroGuardado.idTercero;

                const usuarioData = {
                    login: fullData.correo,
                    contrasena: fullData.contrasena,
                    rol: { idRol: 1 },
                    tercero: { idTercero: idTercero }
                };
                const responseUsuario = await fetch('/api/usuarios', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(usuarioData)
                });
                if (!responseUsuario.ok) {
                    const errorText = await responseUsuario.text();
                    throw new Error(`Error al crear la cuenta de usuario: ${errorText}`);
                }

                const afiliadoData = {
                    tercero: { idTercero: idTercero },
                    fechaAfiliacion: new Date().toISOString().split('T')[0],
                    planSalud: { idPlanSalud: fullData.id_plan_salud }
                };
                const responseAfiliado = await fetch('/api/afiliados', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(afiliadoData)
                });
                if (!responseAfiliado.ok) {
                    const errorText = await responseAfiliado.text();
                    throw new Error(`Error al finalizar la afiliación: ${errorText}`);
                }

                const successModal = new bootstrap.Modal(document.getElementById('registroSuccessModal'));
                successModal.show();
            } catch (error) {
                console.error("Hubo un problema en el registro:", error);
                const errorModal = new bootstrap.Modal(document.getElementById('registroErrorModal'));
                errorModal.querySelector('.modal-body-error').textContent = `Hubo un problema: ${error.message}`;
                errorModal.show();
            } finally {
                submitButton.disabled = false;
                submitButton.innerHTML = originalButtonText;
            }
        });
    }

    // ===================================================================
    // SECCIÓN 3: PANEL DE ADMINISTRADOR (CRUD COMPLETO)
    // ===================================================================
    function cargarTablasAdmin() {
        const tablas = [
            { id: 'tabla-afiliados-body', endpoint: '/api/afiliados', render: renderAfiliado },
            { id: 'tabla-profesionales-body', endpoint: '/api/profesionales', render: renderProfesional },
            { id: 'tabla-administradores-body', endpoint: '/api/usuarios', render: renderAdmin },
            { id: 'tabla-centros-body', endpoint: '/api/centros_salud', render: renderCentro },
            { id: 'tabla-especialidades-body', endpoint: '/api/especialidades', render: renderEspecialidad },
            { id: 'tabla-planes-body', endpoint: '/api/planes-salud', render: renderPlan }
        ];
        tablas.forEach(config => {
            const tablaBody = document.getElementById(config.id);
            if (tablaBody) {
                fetch(config.endpoint)
                    .then(response => {
                        if (!response.ok) throw new Error(`Error ${response.status}`);
                        return response.json();
                    })
                    .then(data => {
                        tablaBody.innerHTML = '';
                        if (config.id === 'tabla-administradores-body') {
                            data = data.filter(u => u.rol && u.rol.nombreRol === 'Administrativo');
                        }
                        if (data.length === 0) {
                            const colspan = tablaBody.closest('table').querySelector('thead th').length;
                            tablaBody.innerHTML = `<tr><td colspan="${colspan}" class="text-center">No hay registros.</td></tr>`;
                            return;
                        }
                        data.forEach(item => tablaBody.innerHTML += config.render(item));
                    })
                    .catch(error => {
                        console.error(`Error al cargar ${config.id}:`, error);
                        const colspan = tablaBody.closest('table').querySelector('thead th').length;
                        tablaBody.innerHTML = `<tr><td colspan="${colspan}" class="text-center text-danger">Error al cargar datos.</td></tr>`;
                    });
            }
        });
    }

    function renderAfiliado(a) { return `<tr><td>${a.tercero.nombre} ${a.tercero.apellido}</td><td>${a.tercero.correo}</td><td>${a.planSalud.nombrePlan}</td><td>${renderBotones('Afiliado', a.idAfiliado)}</td></tr>`; }
    function renderProfesional(p) { return `<tr><td>${p.tercero.nombre} ${p.tercero.apellido}</td><td>${p.especialidad.nombreEspecialidad}</td><td>${renderBotones('Profesional', p.idProfesional)}</td></tr>`; }
    function renderAdmin(u) { return `<tr><td>${u.tercero.nombre} ${u.tercero.apellido}</td><td>${u.login}</td><td>${renderBotones('Administrador', u.idUsuario)}</td></tr>`; }
    function renderCentro(c) { return `<tr><td>${c.nombre}</td><td>${c.direccion}</td><td>${c.telefono}</td><td>${renderBotones('Centro', c.idCentroSalud)}</td></tr>`; }
    function renderEspecialidad(e) { return `<tr><td>${e.nombreEspecialidad}</td><td>${renderBotones('Especialidad', e.idEspecialidad)}</td></tr>`; }
    function renderPlan(p) { return `<tr><td>${p.nombrePlan}</td><td>${p.costoMensual}</td><td>${renderBotones('Plan', p.idPlanSalud)}</td></tr>`; }

    function renderBotones(entidad, id) {
        return `
            <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#formModal" data-action="edit" data-entity="${entidad}" data-id="${id}"><i class="bi bi-pencil-fill"></i></button>
            <button class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" data-entity="${entidad}" data-id="${id}"><i class="bi bi-trash-fill"></i></button>
        `;
    }

    if (window.location.pathname.startsWith('/admin/')) {
        cargarTablasAdmin();
    }

    const confirmModal = document.getElementById('confirmDeleteModal');
    if (confirmModal) {
        let entityToDelete, idToDelete;
        confirmModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            entityToDelete = button.getAttribute('data-entity');
            idToDelete = button.getAttribute('data-id');
            confirmModal.querySelector('.modal-title').textContent = `Confirmar Eliminación`;
            confirmModal.querySelector('.modal-body').innerHTML = `¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.`;
        });
        document.getElementById('confirmDeleteButton')?.addEventListener('click', function () {
            const endpoint = `/api/${getEndpointForEntity(entityToDelete)}/${idToDelete}`;
            fetch(endpoint, { method: 'DELETE' })
                .then(response => {
                    if (!response.ok) { throw new Error('Error al eliminar.'); }
                    bootstrap.Modal.getInstance(confirmModal).hide();
                    location.reload();
                })
                .catch(error => alert('No se pudo eliminar el registro.'));
        });
    }

    function getEndpointForEntity(entity) {
        const endpoints = {
            'Afiliado': 'afiliados', 'Profesional': 'profesionales', 'Administrador': 'usuarios',
            'Centro': 'centros_salud', 'Especialidad': 'especialidades', 'Plan': 'planes-salud'
        };
        return endpoints[entity] || entity.toLowerCase() + 's';
    }

    // ===================================================================
    // FORMULARIOS DINÁMICOS (CRUD)
    // ===================================================================
    const formModal = document.getElementById('formModal');
    if (formModal) {
        const form = formModal.querySelector('#entityForm');
        const modalTitle = formModal.querySelector('.modal-title');

        const formTemplates = {
            Afiliado: () => `
            <h5 class="mb-3 text-primary">Datos Personales</h5>
            <div class="mb-3">
                <label class="form-label">Tipo de Documento</label>
                <select class="form-select" name="tipoDocumento" required>
                    <option value="">Seleccione...</option>
                    <option value="CC">Cédula de Ciudadanía</option>
                    <option value="TI">Tarjeta de Identidad</option>
                    <option value="CE">Cédula de Extranjería</option>
                </select>
            </div>
            <div class="mb-3"><label class="form-label">Número de Documento</label><input type="text" class="form-control" name="numeroDocumento" required></div>
            <div class="mb-3"><label class="form-label">Nombre</label><input type="text" class="form-control" name="nombre" required></div>
            <div class="mb-3"><label class="form-label">Apellido</label><input type="text" class="form-control" name="apellido" required></div>
            <div class="mb-3"><label class="form-label">Teléfono</label><input type="text" class="form-control" name="telefono" required></div>
            <div class="mb-3"><label class="form-label">Correo Electrónico</label><input type="email" class="form-control" name="correo" required></div>
            <div class="mb-3"><label class="form-label">Contraseña</label><input type="password" class="form-control" name="contrasena" required></div>
            <h5 class="mt-4 mb-3 text-primary">Datos de Afiliación</h5>
            <div class="mb-3"><label class="form-label">Plan de Salud</label><select class="form-select" name="idPlanSalud" required></select></div>
        `,
            Profesional: () => `
            <h5 class="mb-3 text-primary">Datos Personales</h5>
            <div class="mb-3"><label class="form-label">Tipo de Documento</label>
                <select class="form-select" name="tipoDocumento" required>
                    <option value="CC">Cédula de Ciudadanía</option>
                    <option value="TI">Tarjeta de Identidad</option>
                    <option value="CE">Cédula de Extranjería</option>
                </select>
            </div>
            <div class="mb-3"><label class="form-label">Número de Documento</label><input type="text" class="form-control" name="numeroDocumento" required></div>
            <div class="mb-3"><label class="form-label">Nombre</label><input type="text" class="form-control" name="nombre" required></div>
            <div class="mb-3"><label class="form-label">Apellido</label><input type="text" class="form-control" name="apellido" required></div>
            <div class="mb-3"><label class="form-label">Teléfono</label><input type="text" class="form-control" name="telefono" required></div>
            <div class="mb-3"><label class="form-label">Correo Electrónico</label><input type="email" class="form-control" name="correo" required></div>
            <h5 class="mt-4 mb-3 text-primary">Datos Profesionales</h5>
            <div class="mb-3"><label class="form-label">Especialidad</label><select class="form-select" name="idEspecialidad" required></select></div>
            <div class="mb-3"><label class="form-label">Centro de Salud</label><select class="form-select" name="idCentroSalud" required></select></div>
        `,
            Administrador: () => `
            <h5 class="mb-3 text-primary">Datos Personales</h5>
            <div class="mb-3"><label class="form-label">Tipo de Documento</label>
                <select class="form-select" name="tipoDocumento" required>
                    <option value="CC">Cédula de Ciudadanía</option>
                    <option value="TI">Tarjeta de Identidad</option>
                    <option value="CE">Cédula de Extranjería</option>
                </select>
            </div>
            <div class="mb-3"><label class="form-label">Número de Documento</label><input type="text" class="form-control" name="numeroDocumento" required></div>
            <div class="mb-3"><label class="form-label">Nombre</label><input type="text" class="form-control" name="nombre" required></div>
            <div class="mb-3"><label class="form-label">Apellido</label><input type="text" class="form-control" name="apellido" required></div>
            <div class="mb-3"><label class="form-label">Teléfono</label><input type="text" class="form-control" name="telefono" required></div>
            <div class="mb-3"><label class="form-label">Correo Electrónico</label><input type="email" class="form-control" name="correo" required></div>
            <h5 class="mt-4 mb-3 text-primary">Datos de Acceso</h5>
            <div class="mb-3"><label class="form-label">Contraseña</label><input type="password" class="form-control" name="contrasena" required></div>
        `,
            Plan: () => `
    <div class="mb-3">
        <label class="form-label">Nombre del Plan</label>
        <input type="text" class="form-control" name="nombrePlan" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Descripción</label>
        <textarea class="form-control" name="descripcion" rows="3" required></textarea>
    </div>
    <div class="mb-3">
        <label class="form-label">Costo Mensual</label>
        <input type="number" class="form-control" step="0.01" name="costoMensual" required>
    </div>
`,

            Especialidad: () => `
            <div class="mb-3"><label class="form-label">Nombre de la Especialidad</label><input type="text" class="form-control" name="nombreEspecialidad" required></div>
        `,
            Centro: () => `
            <div class="mb-3"><label class="form-label">Nombre</label><input type="text" class="form-control" name="nombre" required></div>
            <div class="mb-3"><label class="form-label">Dirección</label><input type="text" class="form-control" name="direccion" required></div>
            <div class="mb-3"><label class="form-label">Teléfono</label><input type="text" class="form-control" name="telefono" required></div>
        `
        };

        formModal.addEventListener('show.bs.modal', async function (event) {
            const button = event.relatedTarget;
            const action = button.getAttribute('data-action');
            const entity = button.getAttribute('data-entity');
            const id = button.getAttribute('data-id');

            form.setAttribute('data-current-action', action);
            form.setAttribute('data-current-entity', entity);
            form.setAttribute('data-current-id', id || '');
            modalTitle.textContent = action === 'add' ? `Agregar ${entity}` : `Editar ${entity}`;

            form.innerHTML = formTemplates[entity]();
            populateAdminSelects();
        });

        form.addEventListener('submit', async function (event) {
            event.preventDefault();
            const action = form.getAttribute('data-current-action');
            const entity = form.getAttribute('data-current-entity');
            const id = form.getAttribute('data-current-id');

            const formData = new FormData(form);
            const fullData = Object.fromEntries(formData.entries());

            try {
                // === FLUJOS PERSONALIZADOS ===
                if (entity === 'Afiliado') {
                    // Crear Tercero
                    const terceroData = {
                        tipoDocumento: fullData.tipoDocumento,
                        numeroDocumento: fullData.numeroDocumento,
                        nombre: fullData.nombre,
                        apellido: fullData.apellido,
                        telefono: fullData.telefono,
                        correo: fullData.correo
                    };
                    const responseTercero = await fetch('/api/terceros', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(terceroData) });
                    if (!responseTercero.ok) throw new Error(await responseTercero.text());
                    const terceroGuardado = await responseTercero.json();

                    // Crear Usuario
                    const usuarioData = {
                        login: fullData.correo,
                        contrasena: fullData.contrasena,
                        rol: { idRol: 1 },
                        tercero: { idTercero: terceroGuardado.idTercero }
                    };
                    const responseUsuario = await fetch('/api/usuarios', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(usuarioData) });
                    if (!responseUsuario.ok) throw new Error(await responseUsuario.text());

                    // Crear Afiliado
                    const afiliadoData = {
                        tercero: { idTercero: terceroGuardado.idTercero },
                        fechaAfiliacion: new Date().toISOString().split('T')[0],
                        planSalud: { idPlanSalud: fullData.idPlanSalud }
                    };
                    const responseAfiliado = await fetch('/api/afiliados', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(afiliadoData) });
                    if (!responseAfiliado.ok) throw new Error(await responseAfiliado.text());
                }

                if (entity === 'Profesional') {
                    // Crear Tercero
                    const terceroData = {
                        tipoDocumento: fullData.tipoDocumento,
                        numeroDocumento: fullData.numeroDocumento,
                        nombre: fullData.nombre,
                        apellido: fullData.apellido,
                        telefono: fullData.telefono,
                        correo: fullData.correo
                    };
                    const responseTercero = await fetch('/api/terceros', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(terceroData) });
                    if (!responseTercero.ok) throw new Error(await responseTercero.text());
                    const terceroGuardado = await responseTercero.json();

                    // Crear Profesional
                    const profesionalData = {
                        tercero: { idTercero: terceroGuardado.idTercero },
                        especialidad: { idEspecialidad: fullData.idEspecialidad },
                        centroSalud: { idCentroSalud: fullData.idCentroSalud }
                    };
                    const responseProf = await fetch('/api/profesionales', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(profesionalData) });
                    if (!responseProf.ok) throw new Error(await responseProf.text());
                }

                if (entity === 'Administrador') {
                    // Crear Tercero
                    const terceroData = {
                        tipoDocumento: fullData.tipoDocumento,
                        numeroDocumento: fullData.numeroDocumento,
                        nombre: fullData.nombre,
                        apellido: fullData.apellido,
                        telefono: fullData.telefono,
                        correo: fullData.correo
                    };
                    const responseTercero = await fetch('/api/terceros', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(terceroData) });
                    if (!responseTercero.ok) throw new Error(await responseTercero.text());
                    const terceroGuardado = await responseTercero.json();

                    // Crear Usuario (Admin)
                    const usuarioData = {
                        login: fullData.correo,
                        contrasena: fullData.contrasena,
                        rol: { idRol: 3 },
                        tercero: { idTercero: terceroGuardado.idTercero }
                    };
                    const responseAdmin = await fetch('/api/usuarios', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(usuarioData) });
                    if (!responseAdmin.ok) throw new Error(await responseAdmin.text());
                }

                // === CRUD SIMPLE (Plan, Especialidad, Centro) ===
                if (['Plan', 'Especialidad', 'Centro'].includes(entity)) {
                    const endpoint = `/api/${getEndpointForEntity(entity)}` + (action === 'edit' ? `/${id}` : '');
                    const method = action === 'edit' ? 'PUT' : 'POST';
                    const response = await fetch(endpoint, {
                        method: method,
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(fullData)
                    });
                    if (!response.ok) throw new Error(await response.text());
                }

                bootstrap.Modal.getInstance(formModal).hide();
                alert('Registro guardado correctamente');
                cargarTablasAdmin();
            } catch (error) {
                console.error(error);
                alert(`Error: ${error.message}`);
            }
        });
    }


    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    // ===================================================================
    // SECCIÓN 4: PANEL DE PROFESIONAL (AGENDA COMPLETA)
    // ===================================================================
    if (window.location.pathname.includes("/profesional/dashboard")) {
        const usuarioLogueado = JSON.parse(sessionStorage.getItem("usuarioLogueado"));
        document.getElementById("nombreProfesional").textContent =
            `Dr. ${usuarioLogueado.tercero.nombre} ${usuarioLogueado.tercero.apellido}`;

        const hoyFecha = new Date();
        document.getElementById("fechaAgenda").textContent =
            `Agenda completa al día de hoy: ${hoyFecha.toLocaleDateString("es-ES", {
                weekday: "long", day: "numeric", month: "long", year: "numeric"
            })}`;

        const contenedor = document.getElementById("contenedorCitas");

        fetch(`/api/citas`)
            .then(res => res.json())
            .then(citas => {
                const misCitas = citas.filter(c =>
                    c.profesional.tercero.idTercero === usuarioLogueado.tercero.idTercero
                );

                contenedor.innerHTML = "";

                if (misCitas.length === 0) {
                    contenedor.innerHTML = `
                    <div class="col-12 text-center">
                        <p class="text-muted">No tienes citas registradas.</p>
                    </div>`;
                    return;
                }

                misCitas.sort((a, b) => {
                    const fechaHoraA = new Date(`${a.fecha}T${a.hora}`);
                    const fechaHoraB = new Date(`${b.fecha}T${b.hora}`);
                    return fechaHoraA - fechaHoraB;
                });

                misCitas.forEach(c => {
                    contenedor.innerHTML += `
                    <div class="col-md-6">
                        <div class="card h-100">
                            <div class="card-header d-flex justify-content-between align-items-center">
                                <strong><i class="bi bi-clock me-2"></i>${c.fecha} ${c.hora}</strong>
                                <span class="badge ${getEstadoBadgeClass(c.estadoCita)}">${c.estadoCita}</span>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${c.afiliado.tercero.nombre} ${c.afiliado.tercero.apellido}</h5>
                                <p class="card-text text-muted">${c.afiliado.tercero.tipoDocumento} ${c.afiliado.tercero.numeroDocumento}</p>
                                <p><strong>Motivo:</strong> ${c.motivo}</p>
                            </div>
                            <div class="card-footer bg-white border-0 text-end">
                                <button class="btn btn-primary btn-atender" data-id="${c.idCita}">Atender Paciente</button>
                            </div>
                        </div>
                    </div>`;
                });

                document.querySelectorAll(".btn-atender").forEach(btn => {
                    btn.addEventListener("click", () => {
                        const idCita = btn.getAttribute("data-id");
                        sessionStorage.setItem("citaSeleccionada", idCita);
                        window.location.href = "/profesional/paciente";
                    });
                });
            })
            .catch(err => {
                console.error("Error al cargar agenda:", err);
                contenedor.innerHTML = `<div class="col-12 text-center text-danger">
                Error al cargar la agenda.
            </div>`;
            });
    }

    function getEstadoBadgeClass(estado) {
        const classes = {
            "Confirmada": "bg-success-subtle text-success-emphasis border border-success-subtle",
            "Pendiente": "bg-warning-subtle text-warning-emphasis border border-warning-subtle",
            "En Sala de Espera": "bg-info-subtle text-info-emphasis border border-info-subtle"
        };
        return classes[estado] || "bg-secondary";
    }

    if (window.location.pathname.includes("/profesional/paciente")) {
        const idCita = sessionStorage.getItem("citaSeleccionada");

        if (!idCita) {
            alert("No se encontró información de la cita seleccionada.");
            window.location.href = "/profesional/dashboard";
        }

        fetch(`/api/citas/${idCita}`)
            .then(res => res.json())
            .then(cita => {
                document.getElementById("nombreProfesional").textContent =
                    `Dr. ${cita.profesional.tercero.nombre} ${cita.profesional.tercero.apellido}`;

                document.getElementById("nombrePaciente").textContent =
                    `${cita.afiliado.tercero.nombre} ${cita.afiliado.tercero.apellido}`;

                document.getElementById("documentoPaciente").textContent =
                    `${cita.afiliado.tercero.tipoDocumento} ${cita.afiliado.tercero.numeroDocumento}`;

                document.getElementById("motivoConsulta").textContent = cita.motivo;
                document.getElementById("fechaCita").textContent = cita.fecha;
                document.getElementById("horaCita").textContent = cita.hora;
            })
            .catch(err => {
                console.error("Error cargando cita:", err);
                alert("No se pudo cargar la cita");
            });

        document.getElementById("finalizarConsulta").addEventListener("click", () => {
            fetch(`/api/citas/${idCita}/finalizar`, { method: "PUT" })
                .then(() => {
                    alert("Consulta finalizada");
                    window.location.href = "/profesional/dashboard";
                })
                .catch(() => alert("Error al finalizar la consulta"));
        });
    }

    function limpiarCamposNoVisibles(form) {
        form.querySelectorAll("input, select, textarea").forEach(el => {
            if (el.offsetParent === null) {
                el.removeAttribute("required");
            }
        });
    }

});
