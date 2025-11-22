// copyConfig.js
const fs = require('fs');
const path = require('path');

const env = process.argv[2] || 'local';
const sourceFile = `config.${env}.js`;
const destFile = 'config.js';

fs.copyFileSync(path.join(__dirname, sourceFile), path.join(__dirname, destFile));
console.log(`✅ Copied ${sourceFile} → ${destFile}`);
