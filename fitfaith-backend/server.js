require('dotenv').config(); // Carga las variables del archivo .env
const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

// Conexión usando las variables de entorno
const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME
});

db.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos:', err);
        return;
    }
    console.log('¡Conectado exitosamente a la base de datos gym_app!');
});

// Ruta de Login
app.post('/api/login', (req, res) => {
    const { email, password } = req.body;
    console.log('📩 Petición HTTP recibida en POST /api/login desde:', req.ip, '| email:', email);

    const query = 'SELECT * FROM usuarios WHERE email = ? AND password = ?';

    db.query(query, [email, password], (err, results) => {
        if (err) {
            console.error('❌ Error en consulta MySQL (/api/login):', err);
            return res.status(500).json({ success: false, message: 'Error en el servidor' });
        }
        if (results.length > 0) {
            console.log('✅ Login atendido con éxito -> Usuario:', results[0].nombre, '| Rol:', results[0].rol);
            res.json({
                success: true,
                message: '¡Bienvenido!',
                usuario: results[0].nombre,
                rol: results[0].rol
            });
        } else {
            console.log('⚠️ Credenciales inválidas para:', email);
            res.json({ success: false, message: 'Correo o contraseña incorrectos' });
        }
    });
});

// Ruta de Registro
app.post('/api/registro', (req, res) => {
    const { nombre, email, password, rol } = req.body;
    console.log('📩 Petición recibida en POST /api/registro -> email:', email);

    if (!nombre || !email || !password) {
        return res.status(400).json({
            success: false,
            message: 'Nombre, email y password son obligatorios'
        });
    }

    const rolFinal = rol || 'usuario';

    // Verificar si el correo ya existe en la tabla usuarios
    const checkQuery = 'SELECT id FROM usuarios WHERE email = ?';
    db.query(checkQuery, [email], (err, existing) => {
        if (err) {
            console.error('❌ Error al verificar correo en /api/registro:', err);
            return res.status(500).json({ success: false, message: 'Error en el servidor' });
        }

        if (existing.length > 0) {
            console.log('⚠️ Intento de registro con correo duplicado:', email);
            return res.status(409).json({
                success: false,
                message: 'El correo electrónico ya está registrado'
            });
        }

        const insertQuery = 'INSERT INTO usuarios (nombre, email, password, rol) VALUES (?, ?, ?, ?)';
        db.query(insertQuery, [nombre, email, password, rolFinal], (insertErr, result) => {
            if (insertErr) {
                console.error('❌ Error al insertar usuario en /api/registro:', insertErr);
                return res.status(500).json({ success: false, message: 'Error al registrar usuario' });
            }

            console.log('✅ Nuevo usuario registrado -> ID:', result.insertId, '| email:', email);
            return res.status(201).json({
                success: true,
                message: 'Usuario registrado exitosamente',
                id: result.insertId,
                usuario: nombre,
                rol: rolFinal
            });
        });
    });
});

app.listen(3000, () => {
    console.log('Servidor backend corriendo en http://localhost:3000');
});