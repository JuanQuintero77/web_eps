/**
 * Archivo JavaScript Principal para la Aplicación Web de la EPS.
 * CRUD Completo funcionando con payloads correctos.
 */

document.addEventListener('DOMContentLoaded', function () {

    // ===================================================================
    // SECCIÓN 0: POBLACIÓN DE SELECTS (PARA PLANES, ESPECIALIDADES, CENTROS)
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
    // SECCIÓN 1: CARGA DE TABLAS EN EL PANEL ADMIN
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
                    .then(response => { if (!response.ok) throw new Error(`Error ${response.status}`); return response.json(); })
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
    function renderCentro(c) { return `<tr><td>${c.nombre}</td><td>${c.direccion}</td><td>${renderBotones('Centro', c.idCentroSalud)}</td></tr>`; }
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

    // ===================================================================
    // SECCIÓN 2: ELIMINAR REGISTROS
    // ===================================================================
    const confirmModal = document.getElementById('confirmDeleteModal');
    if (confirmModal) {
        let entityToDelete, idToDelete;
        confirmModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            entityToDelete = button.getAttribute('data-entity');
            idToDelete = button.getAttribute('data-id');
            confirmModal.querySelector('.modal-body').innerHTML = `¿Estás seguro de que deseas eliminar este registro? Esta acción no se puede deshacer.`;
        });

        confirmModal.querySelector('.btn-danger').addEventListener('click', function () {
            const endpoint = `/api/${getEndpointForEntity(entityToDelete)}/${idToDelete}`;
            fetch(endpoint, { method: 'DELETE' })
                .then(response => {
                    if (!response.ok) throw new Error('Error al eliminar.');
                    bootstrap.Modal.getInstance(confirmModal).hide();
                    cargarTablasAdmin();
                })
                .catch(() => alert('No se pudo eliminar el registro.'));
        });
    }

    function getEndpointForEntity(entity) {
        const endpoints = {
            'Afiliado': 'afiliados',
            'Profesional': 'profesionales',
            'Administrador': 'usuarios',
            'Centro': 'centros_salud',
            'Especialidad': 'especialidades',
            'Plan': 'planesalud'
        };
        return endpoints[entity];
    }

    // ===================================================================
    // SECCIÓN 3: AGREGAR Y EDITAR REGISTROS (GUARDAR)
    // ===================================================================
    const formModal = document.getElementById('formModal');
    if (formModal) {
        const form = formModal.querySelector('#entityForm');
        const modalTitle = formModal.querySelector('.modal-title');

        formModal.addEventListener('show.bs.modal', async function (event) {
            const button = event.relatedTarget;
            const action = button.getAttribute('data-action');
            const entity = button.getAttribute('data-entity');
            const id = button.getAttribute('data-id');

            form.reset();
            populateAdminSelects();

            form.setAttribute('data-action', action);
            form.setAttribute('data-entity', entity);
            form.setAttribute('data-id', id || '');

            form.querySelectorAll('[data-field-for]').forEach(field => field.style.display = 'none');
            form.querySelectorAll(`[data-field-for~="${entity}"]`).forEach(field => field.style.display = 'block');

            modalTitle.textContent = (action === 'add') ? `Agregar ${entity}` : `Editar ${entity}`;

            if (action === 'edit') {
                try {
                    const response = await fetch(`/api/${getEndpointForEntity(entity)}/${id}`);
                    if (!response.ok) throw new Error('No se pudieron cargar los datos para editar.');
                    const data = await response.json();

                    // Poblamos los campos planos y select
                    for (const key in data) {
                        if (form.elements[key]) form.elements[key].value = data[key];
                        if (typeof data[key] === 'object' && data[key] !== null) {
                            for (const nestedKey in data[key]) {
                                if (form.elements[nestedKey]) {
                                    form.elements[nestedKey].value = data[key][nestedKey];
                                }
                                const selectIdField = `id${nestedKey.charAt(0).toUpperCase() + nestedKey.slice(1)}`;
                                if (form.elements[selectIdField]) {
                                    form.elements[selectIdField].value = data[key][selectIdField];
                                }
                            }
                        }
                    }
                } catch (error) {
                    alert(error.message);
                    bootstrap.Modal.getInstance(formModal).hide();
                }
            }
        });

        form.addEventListener('submit', async function (event) {
            event.preventDefault();
            const action = form.getAttribute('data-action');
            const entity = form.getAttribute('data-entity');
            const id = form.getAttribute('data-id');

            const method = (action === 'add') ? 'POST' : 'PUT';
            const endpoint = `/api/${getEndpointForEntity(entity)}${(action === 'edit') ? `/${id}` : ''}`;

            const formData = Object.fromEntries(new FormData(form).entries());
            let payload = {};

            switch (entity) {
                case 'Afiliado':
                    payload = {
                        tercero: {
                            nombre: formData.nombre,
                            apellido: formData.apellido,
                            numeroDocumento: formData.numeroDocumento,
                            correo: formData.correo
                        },
                        planSalud: { idPlanSalud: parseInt(formData.idPlanSalud) },
                        fechaAfiliacion: new Date().toISOString().split('T')[0]
                    };
                    break;

                case 'Profesional':
                    payload = {
                        tercero: {
                            nombre: formData.nombre,
                            apellido: formData.apellido,
                            numeroDocumento: formData.numeroDocumento
                        },
                        especialidad: { idEspecialidad: parseInt(formData.idEspecialidad) },
                        centroSalud: { idCentroSalud: parseInt(formData.idCentroSalud) }
                    };
                    break;

                case 'Administrador':
                    payload = {
                        login: formData.login,
                        contrasena: formData.contrasena || undefined,
                        rol: { idRol: 3 }, 
                        tercero: {
                            nombre: formData.nombre,
                            apellido: formData.apellido,
                            numeroDocumento: formData.numeroDocumento
                        }
                    };
                    break;

                case 'Centro':
                    payload = { nombre: formData.nombre, direccion: formData.direccion };
                    break;

                case 'Especialidad':
                    payload = { nombreEspecialidad: formData.nombreEspecialidad };
                    break;

                case 'Plan':
                    payload = {
                        nombrePlan: formData.nombrePlan,
                        costoMensual: parseFloat(formData.costoMensual)
                    };
                    break;
            }

            console.log("Enviando payload:", payload); 
            try {
                const response = await fetch(endpoint, {
                    method,
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                if (!response.ok) throw new Error(await response.text());
                bootstrap.Modal.getInstance(formModal).hide();
                cargarTablasAdmin(); 
            } catch (error) {
                alert(`Error al guardar: ${error.message}`);
            }
        });

    }
});
