
			octree = new THREE.Octree( {
				
				depthMax: Infinity,
				
				objectsThreshold: 8,
				
				overlapPct: 0.15
			} );
			var simpleGeometry = new THREE.BoxGeometry( 1, 1, 1 );
			for ( var i = 0; i < simpleMeshCount - 1; i++ ) {
				totalFaces += simpleGeometry.faces.length;
				var simpleMaterial = new THREE.MeshBasicMaterial();
				simpleMaterial.color.setHex( baseColor );
				modifyOctree( simpleGeometry, simpleMaterial, false, true, true, true );
			}
		function modifyOctree( geometry, material, useFaces, randomPosition, randomRotation, randomScale ) {
			var mesh;
			if ( geometry ) {
				// create new object
                mesh = new THREE.Mesh(geometry, material);
                
				// give new object a random position, rotation, and scale
				if ( randomPosition ) {
					mesh.position.set( Math.random() * radiusMax - radiusMaxHalf, Math.random() * radiusMax - radiusMaxHalf, Math.random() * radiusMax - radiusMaxHalf );
				}
				if ( randomRotation ) {
					mesh.rotation.set( Math.random() * 2 * Math.PI, Math.random() * 2 * Math.PI, Math.random() * 2 * Math.PI );
				}
				if ( randomScale ) {
					mesh.scale.x = mesh.scale.y = mesh.scale.z = Math.random() * radius * 0.1 + radius * 0.05;
				}
				// add new object to octree and scene
				// NOTE: octree object insertion is deferred until after the next render cycle
				octree.add( mesh, { useFaces: useFaces } );
				scene.add( mesh );
				// store object
				objects.push( mesh );
				/*
				// octree details to console
				console.log( ' ============================================================================================================');
				console.log( ' OCTREE: ', octree );
				console.log( ' ... depth ', octree.depth, ' vs depth end?', octree.depthEnd() );
				console.log( ' ... num nodes: ', octree.nodeCountEnd() );
				console.log( ' ... total objects: ', octree.objectCountEnd(), ' vs tree objects length: ', octree.objects.length );
				console.log( ' ============================================================================================================');
				console.log( ' ');
				// print full octree structure to console
				octree.toConsole();
				*/
			}
		}
		function onWindowResize() {
			camera.aspect = window.innerWidth / window.innerHeight;
			camera.updateProjectionMatrix();
			renderer.setSize( window.innerWidth, window.innerHeight );
		}
		function onDocumentMouseMove( event ) {
			event.preventDefault();
			mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
			mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;
			raycaster.setFromCamera( mouse, camera );
			var octreeObjects;
			var numObjects;
			var numFaces = 0;
			var intersections;
			if ( useOctree ) {
				octreeObjects = octree.search( raycaster.ray.origin, raycaster.ray.far, true, raycaster.ray.direction );
				intersections = raycaster.intersectOctreeObjects( octreeObjects );
				numObjects = octreeObjects.length;
				for ( var i = 0, il = numObjects; i < il; i++ ) {
					numFaces += octreeObjects[ i ].faces.length;
				}
			}
			else {
				intersections = raycaster.intersectObjects( objects );
				numObjects = objects.length;
				numFaces = totalFaces;
			}
			if ( intersections.length > 0 ) {
				if ( intersected != intersections[ 0 ].object ) {
					if ( intersected ) intersected.material.color.setHex( baseColor );
					intersected = intersections[ 0 ].object;
					intersected.material.color.setHex( intersectColor );
				}
				document.body.style.cursor = 'pointer';
			}
			else if ( intersected ) {
				intersected.material.color.setHex( baseColor );
				intersected = null;
				document.body.style.cursor = 'auto';
			}
			// update tracker
			tracker.innerHTML = ( useOctree ? 'Octree search' : 'Search without octree' ) + ' using infinite ray from camera found [ ' + numObjects + ' / ' + objects.length + ' ] objects, [ ' + numFaces + ' / ' + totalFaces + ' ] faces, and [ ' + intersections.length + ' ] intersections.';
		}
	</script>

</body>

</html>