class BasicUnit
{
    constructor(position, sphereRadius, envmap,boxSize, innerTexture)  //位置，外球大小，外球贴图，贴图内正方体大小，内正方体贴图（用canvas创建）,
    {
        var sphereGeo = new THREE.SphereGeometry(sphereRadius, 20, 20);
        var sphereMat = new THREE.MeshBasicMaterial({ envMap: envmap });
        var sphere = new THREE.Mesh(sphereGeo, sphereMat);
        sphere.material.side = THREE.BackSide;
        sphere.material.transparent = true;
        sphere.material.opacity = 0.8;
        sphere.position.set(position.x, position.y, position.z);
        this.outerSphere = sphere;   //外接球

        

        var boxGeo = new THREE.BoxGeometry(boxSize, boxSize, boxSize);
        var boxMat = new THREE.MeshBasicMaterial({ map: innerTexture });
        var box = new THREE.Mesh(boxGeo, boxMat);
        box.material.transparent = true;
        box.material.opacity = 0.2;
        box.position.set(position.x, position.y, position.z);
        this.innerBox = box;         //内部正方体
    }
    AddToScene(scene)
    {
        scene.add(this.outerSphere);
        scene.add(this.innerBox);
    }
    AddToOctree(octree)
    {
        octree.add(this.outerSphere);
    }
    SetKey(keyInDataBase)    //设置标识符
    {
        this.keyInDataBase = keyInDataBase;
        this.outerSphere.name = keyInDataBase;
    }
    SetIndex(index)
    {
        this.index = index;
    }
    GetIndex()
    {
        return this.index;
    }
    GetKey()
    {
        return this.keyInDataBase;
    }
    SetOuterSphereOpacity(opacity)
    {
        this.outerSphere.material.opacity = opacity;
    }
    SetInnerBoxOpacity(opacity)
    {
        this.innerBox.material.opacity = opacity;
    }
    
    UpdateOctree(octree)
    {
        octree.remove(this.outerSphere);
        octree.add(this.outerSphere);
    }
    InnerBoxRotateY(speed)     //内球转动
    {
        this.innerBox.rotation.y += 0.001 * speed;
    }
    RandomMove(timer,speed)   //外球移动
    {

        this.outerSphere.position.x += Math.cos(timer + this.index * 1.1) * speed;
        this.outerSphere.position.y += Math.sin(timer + this.index * 1.2) * speed;
        this.outerSphere.position.z += Math.cos(timer + this.index * 1.3) * speed;
        this.innerBox.position.x = this.outerSphere.position.x;
        this.innerBox.position.y = this.outerSphere.position.y;
        this.innerBox.position.z = this.outerSphere.position.z;
    }
    
}