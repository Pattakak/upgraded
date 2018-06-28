#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;
layout (location = 2) in vec3 aNorm;

out vec2 texCoord;
out vec3 norm;
out vec3 fragPos;

uniform mat4 view;
uniform mat4 projection;


void main() {

//Outputs below
texCoord = aTexCoord;
norm = aNorm;
fragPos = aPos;

gl_Position = projection * view * vec4(aPos, 1.0f);
}
